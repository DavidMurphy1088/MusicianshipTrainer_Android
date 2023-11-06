package com.musicmaster.musicianshiptrainer.models

import java.util.UUID

open class ScoreEntry() {
    var sequence = 0
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

    fun getTimeSliceNotes(staffNum: Int? = null): List<Note> {
        val result = mutableListOf<Note>()
        if (this is TimeSlice) {
            val ts: TimeSlice = this
            val entries = ts.entries
            for (entry in entries) {
                if (entry is Note) {
                    if (staffNum != null) {
                        if (entry.staffNum == staffNum) {
                            result.add(entry)
                        }
                    } else {
                        result.add(entry)
                    }
                }
            }
        }
        return result
    }

}
