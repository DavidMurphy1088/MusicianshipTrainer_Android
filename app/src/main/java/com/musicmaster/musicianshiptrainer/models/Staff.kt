package com.musicmaster.musicianshiptrainer.models

import android.graphics.RectF

enum class StaffType {
    TREBLE,
    BASS
}
enum class QuaverBeamType {
    NONE,
    START,
    MIDDLE,
    END
}

class NoteLayoutPositions(val id: Int) {
    val positions: MutableMap<Note, RectF> = mutableMapOf()

    companion object {
        private var nextId = 0

        fun getShared(): NoteLayoutPositions {
            val lp = NoteLayoutPositions(nextId)
            nextId += 1
            return lp
        }
    }

    fun storePosition(notes: List<Note>, rect: RectF) {
        if (notes.isNotEmpty() && notes[0].beamType != QuaverBeamType.NONE) {
            // Make a copy of the RectF
            val rectCopy = RectF(rect.left, rect.top, rect.right, rect.bottom)
            // Store the position
            positions[notes[0]] = rectCopy
        }
    }
}
class NoteOffsetsInStaffByKey {
    private var noteOffsetByKey: MutableList<String> = mutableListOf()

    init {
        noteOffsetByKey.add("0     0    0    0    0    0    0,1   0     0    0,1  0    0") // C
        noteOffsetByKey.add("0     0    0    0    0    0    0,1   0     0    0,1  0    0")    //C
        noteOffsetByKey.add("0,1   1    0,1  1,0  0,1  1,0  1     0,1   1    0    1,0  0,1")  //C#, D♭
        noteOffsetByKey.add("1     1,1  1    1    1    1    1,1   1     1,1  1    1    1")    //D
        noteOffsetByKey.add("2,-1  2    2,-1 2    1,1  2,0  2     2,-1  2    1,2  2    1,1")  //D#, E♭
        noteOffsetByKey.add("2     2,1  2    2,1  2    2    2,1   2     2,1  2    2,1  2")    //E
        noteOffsetByKey.add("3     3    3    3    3    3    3     3     3    3,1  3    3")    //F
        noteOffsetByKey.add("3,1   4    3,1  4,0  3,1  4,0  4     3,1   4,0  3    4,0  3,1")  //F#, G♭
        noteOffsetByKey.add("4     4,1  4    4    4    4    4,1   4     4    4,1  4    4")    //G
        noteOffsetByKey.add("4,1   5    4,1  5    4,1  5,0  5     4,1   5    4    5,0  4,1")  //G#, A♭
        noteOffsetByKey.add("5     5,1  5    5,1  5    5    5,1   5     5,1  5    5    5")    //A
        noteOffsetByKey.add("6,-1  6    6,-1 6    6,-1 6    6     6,-1  6    6,0  6    5,1")  //A#, B♭
        noteOffsetByKey.add("6     6,1  6    6,1  6    6,1  6,1   6     6,1  6    6,1  6")    //B
    }

    fun getValue(scaleDegree: Int, keyNum: Int): NoteStaffPlacement? {
        if (scaleDegree >= noteOffsetByKey.size) {
            Logger.instance.reportError("Invalid degree $scaleDegree")
            return null
        }
        if (keyNum >= 12) {
            Logger.instance.reportError("Invalid key $scaleDegree")
            return null
        }

        // Split the string by spaces, filter out empty strings
        val scaleDegreeComponentsLine = noteOffsetByKey[scaleDegree].split(" ")
            .filter { it.isNotBlank() }
        val scaleDegreeComponents = scaleDegreeComponentsLine[keyNum]
        val offsetAndAccidental = scaleDegreeComponents.split(",")
        val offset = offsetAndAccidental[0].toIntOrNull()
        offset?.let {
            val accidental = if (offsetAndAccidental.size > 1) offsetAndAccidental[1].toIntOrNull() else null
            return NoteStaffPlacement(offset, accidental = accidental)
        } ?: run {
            Logger.instance.reportError("Invalid data at row:$scaleDegree, col:$keyNum")
            return null
        }
    }
}

class Staff(val score: Score, val type: StaffType, val staffNum:Int, val linesInStaff:Int) {
    //val type: StaffType
    //val staffType = type
    //val staffNum = staffNum
    var noteLayoutPositions = NoteLayoutPositions(id = 0)
    var noteStaffPlacement = mutableListOf<NoteStaffPlacement>()
    var noteOffsetsInStaffByKey = NoteOffsetsInStaffByKey()

    init {
        var lowestNoteValue = 20 // MIDI C0
        var highestNoteValue = 107 // MIDI B7
        var middleNoteValue = if (type == StaffType.TREBLE) 71 else Note.MIDDLE_C - Note.OCTAVE + 2

// Determine the staff placement for each note pitch
        var keyNumber = when (score.key.keySig.accidentalCount) {
            1 -> 7
            2 -> 2
            3 -> 9
            4 -> 4
            5 -> 11
            else -> 0
        }

        //val noteStaffPlacement = mutableListOf<NoteStaffPlacement>()

        for (noteValue in 0..highestNoteValue) {
            if (noteValue == 72) {
                println("=========")
            }
            var placement = NoteStaffPlacement(0)
            noteStaffPlacement.add(placement)
            if (noteValue < middleNoteValue - 6 * Note.OCTAVE || noteValue >= middleNoteValue + 6 * Note.OCTAVE) {
                continue
            }

            var offsetFromTonic = (noteValue - Note.MIDDLE_C) % Note.OCTAVE
            if (offsetFromTonic < 0) {
                offsetFromTonic += 12
            }

            val noteOffset = noteOffsetsInStaffByKey.getValue(scaleDegree = offsetFromTonic, keyNumber)
            if (noteOffset == null) {

            }
//                ?: run {
//                    Logger.instance.reportError("No note offset data for note $noteValue")
//                    //break
//                }
            var offsetFromMidLine = noteOffset!!.offsetFromStaffMidline

            val referenceNote = if (type == StaffType.TREBLE) Note.MIDDLE_C else Note.MIDDLE_C - 2 * Note.OCTAVE
            val octave = if (noteValue >= referenceNote) {
                (noteValue - referenceNote) / Note.OCTAVE
            } else {
                val tmpOctave = (referenceNote - noteValue) / Note.OCTAVE
                tmpOctave - 1
            }
            offsetFromMidLine += (octave - 1) * 7
            offsetFromMidLine += if (type == StaffType.TREBLE) 1 else -1
            placement.offsetFromStaffMidline = offsetFromMidLine

            placement.accidental = noteOffset.accidental
            noteStaffPlacement[noteValue] = placement
        }
    }

    // Tell a note how to display itself
    // Note offset from the middle of the staff is dependent on the staff
    fun getNoteViewPlacement(note: Note): NoteStaffPlacement {
        val defaultPlacement = noteStaffPlacement[note.midiNumber]
        val placement = NoteStaffPlacement(
            defaultPlacement.offsetFromStaffMidline,
            accidental = defaultPlacement.accidental
        )
        return placement
    }
}
