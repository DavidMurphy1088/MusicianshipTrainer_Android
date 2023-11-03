package com.musicmaster.musicianshiptrainer.models

enum class QuaverBeamType {
    // Define the enum values according to the Swift enum
    none, // Example value
    // ... other values
}

enum class StemDirection {
    // Define the enum values according to the Swift enum
    up, // Example value
    // ... other values
}

class NoteStaffPlacement {
    // Define the properties and functions according to the Swift class
}

class Note(
    timeSlice: TimeSlice, // Now passing TimeSlice to the constructor
    midiNumber: Int,
    value: Double,
    staffNum: Int = 0, // Providing a default value similar to TimeSliceEntry, which can be overridden
    accidental: Int? = null,
    rotated: Boolean = false
) : TimeSliceEntry(timeSlice, value, staffNum) {

    companion object {
        const val MIDDLE_C = 60 // Midi pitch for C4
        const val OCTAVE = 12

        const val VALUE_SEMIQUAVER = 0.25
        const val VALUE_QUAVER = 0.5
        const val VALUE_QUARTER = 1.0
        const val VALUE_HALF = 2.0
        const val VALUE_WHOLE = 4.0
    }

    var midiNumber:Int = midiNumber
    var isOnlyRhythmNote = false
    var noteStaffPlacements: Array<NoteStaffPlacement?> = arrayOf(null, null)
    var beamType: QuaverBeamType = QuaverBeamType.none
    var stemDirection: StemDirection = StemDirection.up
    var stemLength: Double = 0.0
    var beamEndNote: Note? = null

}
