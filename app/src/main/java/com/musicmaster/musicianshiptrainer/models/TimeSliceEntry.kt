package com.musicmaster.musicianshiptrainer.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.UUID

open class TimeSliceEntry(
    var timeSlice: TimeSlice,
    var value: Double,
    var staffNum: Int = 0
) : ViewModel() {

    var hilite by mutableStateOf(false)
    var sequence: Int by mutableStateOf(0)  // the timeslice's sequence position
    val id = UUID.randomUUID()

    fun isDotted(): Boolean {
        return listOf(0.75, 1.5, 3.0).contains(value)
    }

    fun getColor(staff: Staff): Color {
        timeSlice.let {
            return when (it.statusTag) {
                StatusTag.IN_ERROR -> Color.Red
                StatusTag.AFTER_ERROR -> Color.LightGray
                StatusTag.HIGHLIGHT_AS_CORRECT -> Color(0f, 0.6f, 0f)
                else -> if (staffNum == staff.staffNum) Color.Black else Color.Transparent
            }
        }
        return Color.Black
    }

    fun getNoteValueName(): String {
        var name = if (this.isDotted()) "dotted " else ""
        name += when (this.value) {
            0.25 -> "semi quaver"
            0.50 -> "quaver"
            1.0 -> "crotchet"
            1.5 -> "dotted crotchet"
            2.0 -> "minim"
            3.0 -> "dotted minim"
            4.0 -> "semibreve"
            else -> "unknown value $value"
        }
        return name
    }

    companion object {
        fun getValueName(value: Double): String {
            var name = when (value) {
                0.25 -> "semi quaver"
                0.50 -> "quaver"
                1.0 -> "crotchet"
                1.5 -> "dotted crotchet"
                2.0 -> "minim"
                3.0 -> "dotted minim"
                4.0 -> "semibreve"
                else -> "unknown value $value"
            }
            return name
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as TimeSliceEntry
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

//    fun getValue(): Double {
//        return value
//    }
}
