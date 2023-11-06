package com.musicmaster.musicianshiptrainer.models

class Key(var keySig: KeySignature, var type: KeyType) {

    enum class KeyType {
        MAJOR, MINOR
    }

    fun getKeyName(): String {
        var desc = ""
        // ... The rest of your logic for getKeyName
        // Make sure to properly handle the `Score.accFlat` equivalent in Kotlin.
        return desc
    }

    fun getKeyTagName(): String {
        // ... Logic for getKeyTagName
        return "someTag"
    }

    fun firstScaleNote(): Int {
        // ... Logic for firstScaleNote
        return 60 // Default value or calculated one
    }

    fun getScaleStartMidi(): Int {
        // ... Logic for getScaleStartMidi
        return 48 // Default value or calculated one
    }

//    fun makeTriadAt(timeSlice: TimeSlice, rootMidi: Int, value: Double, staffNum: Int): List<Note> {
//        // ... Logic for makeTriadAt
//        return listOf()
//    }

    fun getTriadNotes(triadSymbol: String): String {
        // ... Logic for getTriadNotes
        return "someNotes"
    }

    fun hasNote(note: Int): Boolean {
        keySig.sharps.forEach { n ->
            val octaves = Note.getAllOctaves(n)
            if (note in octaves) {
                return true
            }
        }
        return false
    }

    // For equality check, override equals and hashCode methods.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key

        if (type != other.type) return false
        if (keySig.accidentalCount != other.keySig.accidentalCount) return false
        if (keySig.accidentalType != other.keySig.accidentalType) return false

        return true
    }

//    override fun hashCode(): Int {
//        var result = type.hashCode()
//        result = 31 * result + keySig.accidentalCount.hashCode()
//        return result
//    }
//
//    companion object {
//        // Companion object for static content, like Swift's static funcs
//        // Any static methods would go here.
//    }
}
