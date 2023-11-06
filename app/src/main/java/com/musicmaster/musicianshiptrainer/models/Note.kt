package com.musicmaster.musicianshiptrainer.models

enum class StemDirection {
    UP,
    DOWN
}

class NoteStaffPlacement(
    var offsetFromStaffMidline: Int,
    var accidental: Int? = null
) {
}

class Note(
    timeSlice: TimeSlice, // Now passing TimeSlice to the constructor
    midiNumber: Int,
    value: Double,
    staffNum: Int = 0, // Providing a default value similar to TimeSliceEntry, which can be overridden
) : TimeSliceEntry(timeSlice, value, staffNum) {

    companion object {
        const val MIDDLE_C = 60 // Midi pitch for C4
        const val OCTAVE = 12

        const val VALUE_SEMIQUAVER = 0.25
        const val VALUE_QUAVER = 0.5
        const val VALUE_QUARTER = 1.0
        const val VALUE_HALF = 2.0
        const val VALUE_WHOLE = 4.0

        fun getAllOctaves(note: Int): List<Int> {
            val notes = mutableListOf<Int>()
            for (n in 0..88) {
                if ((note >= n && (note - n) % 12 == 0) || (note < n && (n - note) % 12 == 0)) {
                    notes.add(n)
                }
            }
            return notes
        }
    }

    var midiNumber:Int = midiNumber
    var isOnlyRhythmNote = false
    //var noteStaffPlacements: Array<NoteStaffPlacement> = arrayOf()
    var noteStaffPlacements: MutableList<NoteStaffPlacement> = mutableListOf()

    var beamType: QuaverBeamType = QuaverBeamType.NONE
    var stemDirection: StemDirection = StemDirection.UP
    var stemLength: Double = 0.0
    var beamEndNote: Note? = null
    val rotated: Boolean = false
    val accidental: Int? = null

    fun setNotePlacementAndAccidental(staff:Staff, barAlreadyHasNote:Boolean) {
        val defaultNoteData = staff.getNoteViewPlacement(note = this)
        var offsetFromMiddle = defaultNoteData.offsetFromStaffMidline
        var offsetAccidental: Int? = null

        if (this.isOnlyRhythmNote) {
            offsetFromMiddle = 0
        }
        this.accidental?.let { writtenAccidental ->
            // Content provided a specific accidental
            offsetAccidental = writtenAccidental
            if (writtenAccidental != defaultNoteData.accidental) {
                val defaultNoteStaffPlacement = staff.noteStaffPlacement[this.midiNumber]
                val targetOffsetIndex = this.midiNumber - writtenAccidental
                val targetNoteStaffPlacement = staff.noteStaffPlacement[targetOffsetIndex]
                val adjustOffset = defaultNoteStaffPlacement.offsetFromStaffMidline - targetNoteStaffPlacement.offsetFromStaffMidline
                offsetFromMiddle -= adjustOffset
            }
        } ?: run {
            // Determine if the note's accidental is implied by the key signature
            // Or a note has to have a natural accidental to offset the key signature
            val keySignatureHasNote = staff.score.key.hasNote(note = this.midiNumber)
            defaultNoteData.accidental?.let { defaultAccidental ->
                if (!keySignatureHasNote) {
                    if (!barAlreadyHasNote) {
                        offsetAccidental = defaultAccidental
                    }
                }
            } ?: run {
                if (staff.score.key.hasNote(note = this.midiNumber + 1)) {
                    if (!barAlreadyHasNote) {
                        offsetAccidental = 0
                    }
                }
            }
        }

        val placement = NoteStaffPlacement(offsetFromMiddle, accidental = offsetAccidental)
        noteStaffPlacements.add(placement)
    }

}
