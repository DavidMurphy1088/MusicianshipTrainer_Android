package com.musicmaster.musicianshiptrainer.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class StatusTag {
    NO_TAG,
    IN_ERROR,
    AFTER_ERROR,
    HIGHLIGHT_AS_CORRECT
}

class TagHigh(content: String, popup: String?) : ViewModel() {
    var content by mutableStateOf(content)
    var popup: String? = popup
}

class TimeSlice(
    var score: Score
) : ScoreEntry() {
    var entries by mutableStateOf(listOf<TimeSliceEntry>())
    var tagHigh by mutableStateOf<TagHigh?>(null)
    var tagLow by mutableStateOf<String?>(null)
    var notesLength by mutableStateOf<Int?>(null)
    var statusTag by mutableStateOf(StatusTag.NO_TAG)

    var footnote by mutableStateOf<String?>(null)
    var barLine by mutableStateOf(0)
    var beatNumber by mutableStateOf(0.0) // the beat in the bar that the timeslice is at
    var tapDuration by mutableStateOf(0.0) // Used when recording a tap sequence into a score

    //fun getValue(): Double = entries.firstOrNull()?.getValue() ?: 0.0

    fun addNote(n: Note) {
        n.timeSlice = this
        entries = entries + n
        score.let { score ->
            //val barAlreadyHasNote = score.noteCountForBar(pitch = n.midiNumber) > 1
            score.staffs.forEach { staff ->
                n.setNotePlacementAndAccidental(staff, false)
            }
//            score.updateStaffs()
//            score.addStemAndBeamCharacteristics()
        }
    }

//    fun addRest(rest: Rest) {
//        entries = entries + rest
//        score?.updateStaffsAndCharacteristics()
//    }

//    fun addChord(c: Chord) {
//        c.getNotes().forEach(this::addNote)
//        score?.updateStaffsAndCharacteristics()
//    }

    fun setTags(high: TagHigh, low: String) {
        tagHigh = high
        tagLow = low
    }

//    private fun Score.updateStaffsAndCharacteristics() {
//        updateStaffs()
//        addStemAndBeamCharacteristics()
//    }
}

