package com.musicmaster.musicianshiptrainer.models

import java.util.UUID

open class ScoreEntry() {
    val sequence = 0
    val id = UUID.randomUUID()

    fun getTimeSliceEntries(): List<TimeSliceEntry> {
        val result = mutableListOf<TimeSliceEntry>()
        if (this is TimeSlice) {
            val entries = this.entries
            // Assuming entries is a List<TimeSliceEntry>
            result.addAll(entries)
        }
        return result
    }

    fun getTimeSliceNotes(): List<Note> {
        val result = mutableListOf<Note>()
        if (this is TimeSlice) {
            val entries = this.entries
            // Assuming entries is a List<TimeSliceEntry>
            for (entry in entries) {
                if (entry is Note) {
                    result.add(entry)
                }
            }
        }
        return result
    }

}
