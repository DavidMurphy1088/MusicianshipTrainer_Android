package com.musicmaster.musicianshiptrainer.models

import androidx.compose.runtime.mutableStateListOf
import java.util.UUID
class StudentFeedback {
}

class Score(
    var key: Key,
    var timeSignature: TimeSignature,
    linesPerStaff: Int
) {
    val id: UUID = UUID.randomUUID()

    //var barLayoutPositions by mutableStateOf(BarLayoutPositions())
    //var barEditor by mutableStateOf<BarEditor?>(null)

    var scoreEntries = mutableStateListOf<ScoreEntry>()
    //var staffLayoutSize by mutableStateOf(StaffLayoutSize())

    val ledgerLineCount = 2 // 3 or 4 is required to represent low E
    var staffs = mutableListOf<Staff>()

    var studentFeedback: StudentFeedback? = null
    var tempo: Int? = null

    //private var totalStaffLineCount: Int = linesPerStaff + (2 * ledgerLineCount)
    var label: String? = null

    var lineSpacing = 20.0
    var totalStaffLineCount = 0

    init {
        totalStaffLineCount = linesPerStaff + (2*ledgerLineCount)
    }

    fun createTimeSlice(): TimeSlice {
        val timeSlice = TimeSlice(this)
        timeSlice.sequence = scoreEntries.size
        scoreEntries.add(timeSlice)
        return timeSlice
    }

    fun getStaffHeight(score: Score): Double {
        // The following line assumes there's a getTotalStaffLineCount() function in your Score class.
        val height = (score.totalStaffLineCount + 2) * lineSpacing
        return height
    }

    companion object {
        const val accSharp = "\u266f"
        const val accNatural = "\u266e"
        const val accFlat = "\u266d"
    }

    fun createStaff(num: Int, staff: Staff) {
        if (staffs.size <= num) {
            staffs.add(staff)
        } else {
            staffs[num] = staff
        }
    }

    fun getLastTimeSlice(): TimeSlice? {
        for (index in scoreEntries.indices.reversed()) {
            val element = scoreEntries[index]
            if (element is TimeSlice) {
                return element
            }
        }
        return null
    }
    // For each time
    // slice, calculate its beat number in its bar
    fun addBeatValues() {
        var beatCtr = 0.0
        for (i in scoreEntries.indices) {
            val entry = scoreEntries[i]
            when (entry) {
                is BarLine -> beatCtr = 0.0
                is TimeSlice -> {
                    entry.beatNumber = beatCtr
                    beatCtr += entry.getValue()
                }
            }
        }
    }

    fun addStemAndBeamCharacteristics() {
        val timeSlice = getLastTimeSlice() ?: return
        if (timeSlice.entries.isEmpty()) {
            return
        }
        addBeatValues()
        if (timeSlice.entries[0] is Note) {
            addStemCharacteristics()
        }
    }

    fun getAllTimeSlices(): List<TimeSlice> {
        val result = mutableListOf<TimeSlice>()
        for (scoreEntry in this.scoreEntries) {
            if (scoreEntry is TimeSlice) {
                result.add(scoreEntry)
            }
        }
        return result
    }

    // Determine if the stem for the note(s) should go up or down
    fun getStemDirection(staff: Staff, notes: List<Note>): StemDirection {
        var totalOffsets = 0
        for (n in notes) {
            if (n.staffNum == staff.staffNum) {
                val placement = staff.getNoteViewPlacement(note = n)
                totalOffsets += placement.offsetFromStaffMidline
            }
        }
        return if (totalOffsets <= 0) StemDirection.UP else StemDirection.DOWN
    }
    // Determine wh
    // ether quavers can be beamed within a bar's strong and weak beats
    fun canBeBeamedTo(timeSignature: TimeSignature, startBeamTimeSlice: TimeSlice, lastBeat: Double): Boolean {
        when (timeSignature.top) {
            4 -> {
                val startBeatInt = startBeamTimeSlice.beatNumber.toInt()
                return if (lastBeat > 2) {
                    startBeatInt in 2..3
                } else {
                    startBeatInt in 0..1
                }
            }
            3 -> {
                return startBeamTimeSlice.beatNumber.toInt() == lastBeat.toInt()
            }
            2 -> {
                val startBeatInt = startBeamTimeSlice.beatNumber.toInt()
                // Assuming the commented logic is not needed
                return startBeatInt in 0..1
            }
            else -> return false
        }
    }

    /**
     * If the last note added was a quaver, identify any previous adjoining quavers and set them to be joined with a quaver bar.
     * Set the beginning, middle, and end quavers for the beam.
     */
    private fun addStemCharacteristics() {
        val lastNoteIndex = scoreEntries.size - 1
        val scoreEntry = scoreEntries[lastNoteIndex]

        val lastTimeSlice = scoreEntry as? TimeSlice ?: return
        val notes = lastTimeSlice.getTimeSliceNotes()
        if (notes.isEmpty()) {
            return
        }

        val lastNote = notes[0]
        lastNote.sequence = getAllTimeSlices().size

        // The number of staff lines for a full stem length
        val stemLengthLines = 3.5

        if (lastNote.value != Note.VALUE_QUAVER) {
            staffs.forEachIndexed { staffIndex, staff ->
                val stemDirection = getStemDirection(staff, notes)
                val staffNotes = lastTimeSlice.getTimeSliceNotes(staffIndex)
                staffNotes.forEach { note ->
                    note.stemDirection = stemDirection
                    note.stemLength = stemLengthLines
                    // Don't try yet to beam semiquavers
                    if (lastNote.value == Note.VALUE_SEMIQUAVER) {
                        note.beamType = QuaverBeamType.END
                    }
                }
            }
            return
        }

        val staff = staffs[lastNote.staffNum]
        // apply the quaver beam back from the last note
        var notesUnderBeam = mutableListOf<Note>()
        notesUnderBeam.add(lastNote)

        // Figure out the start, middle, and end of this group of quavers
        for (i in lastNoteIndex - 1 downTo 0) {
            val entry = scoreEntries[i]
            if (entry !is TimeSlice) break

            val timeSlice = entry
            if (timeSlice.entries.isNotEmpty() && timeSlice.entries[0] is Rest) break

            val sliceNotes = timeSlice.getTimeSliceNotes()
            if (sliceNotes.isNotEmpty()) {
                val note = sliceNotes[0]
                if (note.value == Note.VALUE_QUAVER) {
                    if (!canBeBeamedTo(timeSignature, timeSlice, lastTimeSlice.beatNumber)) break
                    notesUnderBeam.add(note)
                } else break
            }
        }

        // Check if beam is valid
        var totalValueUnderBeam = 0.0
        var valid = true

        for (note in notesUnderBeam) {
            totalValueUnderBeam += note.value
        }

        if (valid) {
            valid = totalValueUnderBeam % 1.0 == 0.0
        }

        // It's not valid so unbeam
        if (!valid) {
            notesUnderBeam = mutableListOf(lastNote)
        }

        // Determine if the quaver group has up or down stems based on the overall staff placement of the group
        var totalOffset = 0
        notesUnderBeam.forEach { note ->
            val placement = staff.getNoteViewPlacement(note)
            totalOffset += placement.offsetFromStaffMidline
        }

        // Set each note's beam type and calculate the net above or below the staff line for the quaver group
        val startNote = notesUnderBeam[0]
        val startPlacement = staff.getNoteViewPlacement(startNote)
        val endNote = notesUnderBeam.last()
        val endPlacement = staff.getNoteViewPlacement(endNote)
        var beamSlope = (endPlacement.offsetFromStaffMidline - startPlacement.offsetFromStaffMidline).toDouble() / (notesUnderBeam.size - 1)
        var requiredBeamPosition = startPlacement.offsetFromStaffMidline.toDouble()

        notesUnderBeam.forEachIndexed { i, note ->
            when (i) {
                0 -> {
                    note.beamType = QuaverBeamType.END
                    note.stemLength = stemLengthLines
                }
                notesUnderBeam.size - 1 -> {
                    note.beamType = QuaverBeamType.START
                    note.stemLength = stemLengthLines
                }
                else -> {
                    note.beamType = QuaverBeamType.MIDDLE
                    val placement = staff.getNoteViewPlacement(note)
                    // adjust the stem length according to where the note is positioned vs. where the beam slope position requires
                    val stemDiff = placement.offsetFromStaffMidline.toDouble() - requiredBeamPosition
                    note.stemLength = stemLengthLines + stemDiff / 2.0 * if (totalOffset > 0) 1.0 else -1.0
                }
            }
            requiredBeamPosition += beamSlope
            note.stemDirection = if (totalOffset > 0) StemDirection.DOWN else StemDirection.UP
        }

        // Check no stranded beam starts. Every beam start must have a beam end so it is rendered correctly.
        getAllTimeSlices().forEachIndexed { i, ts ->
            ts.getTimeSliceNotes().firstOrNull()?.let { note ->
                if (note.beamType == QuaverBeamType.START) {
                    val nextNote = (i + 1).takeIf { it < getAllTimeSlices().size }?.let { getAllTimeSlices()[it].getTimeSliceNotes().firstOrNull() }
                    if (nextNote?.beamType !in listOf(QuaverBeamType.END, QuaverBeamType.MIDDLE)) {
                        note.beamType = QuaverBeamType.END
                    }
                }
            }
        }
    }

}
