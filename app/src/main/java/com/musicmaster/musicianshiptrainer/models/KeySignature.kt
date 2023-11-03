package com.musicmaster.musicianshiptrainer.models

enum class AccidentalType {
    SHARP,
    FLAT
}

class KeySignature(val accidentalType: AccidentalType, keyName: String) {
    val sharps: MutableList<Int> = mutableListOf() // Notes of this pitch don't require individual accidentals, their accidental is implied by the key signature
    var accidentalCount: Int = 0
    private val maxAccidentals = 7

    init {
        if (keyName.isNotEmpty()) {
            if (keyName !in listOf("C", "G", "D", "A", "E", "B")) {
                // Substitute with Kotlin's logging mechanism
                Logger.error("Unknown Key $keyName")
            }
        }
        when (keyName) {
            "G" -> {
                accidentalCount = 1
                sharps.add(Note.MIDDLE_C + 6) // F#
            }
            "D" -> {
                accidentalCount = 2
                sharps.add(Note.MIDDLE_C + 6) // F#
                sharps.add(Note.MIDDLE_C + 1) // C#
            }
            "A" -> {
                accidentalCount = 3
                sharps.add(Note.MIDDLE_C + 6) // F#
                sharps.add(Note.MIDDLE_C + 1) // C#
                sharps.add(Note.MIDDLE_C + 7) // G#
            }
            "E" -> {
                accidentalCount = 4
                sharps.add(Note.MIDDLE_C + 6) // F#
                sharps.add(Note.MIDDLE_C + 1) // C#
                sharps.add(Note.MIDDLE_C + 8) // G#
                sharps.add(Note.MIDDLE_C + 3) // D#
            }
            "B" -> {
                accidentalCount = 5
                sharps.add(Note.MIDDLE_C + 6) // F#
                sharps.add(Note.MIDDLE_C + 1) // C#
                sharps.add(Note.MIDDLE_C + 8) // G#
                sharps.add(Note.MIDDLE_C + 3) // D#
                sharps.add(Note.MIDDLE_C + 10) // A#
            }
        }
    }
}
