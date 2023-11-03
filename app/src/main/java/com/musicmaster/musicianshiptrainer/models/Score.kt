package com.musicmaster.musicianshiptrainer.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.util.UUID
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentFeedback {
}

class StaffLayoutSize : ViewModel() {
    var lineSpacing by mutableStateOf(0.0)

    init {
        lineSpacing = 20.0
    }
    suspend fun setLineSpacing(v: Double) {
        // Assuming this function is called from a coroutine or another suspend function
        withContext(Dispatchers.Main) {
            lineSpacing = v
        }
    }

    suspend fun getStaffHeight(score: Score): Double {
        // The following line assumes there's a getTotalStaffLineCount() function in your Score class.
        val height = (score.totalStaffLineCount + 2) * lineSpacing
        return height
    }
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

    var staffLayoutSize by mutableStateOf(StaffLayoutSize())
    var totalStaffLineCount = 0

    init {
        totalStaffLineCount = linesPerStaff + (2*ledgerLineCount)
    }

    fun createTimeSlice(sequence: Int): TimeSlice {
        val timeSlice = TimeSlice(this)
        scoreEntries.add(timeSlice)
        return timeSlice
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

}
