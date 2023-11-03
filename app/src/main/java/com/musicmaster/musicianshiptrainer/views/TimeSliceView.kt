package com.musicmaster.musicianshiptrainer.views

import android.provider.ContactsContract
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.TimeSlice

@Composable

fun TimeSliceView(timeSlice: TimeSlice, staff: Staff, lineSpacing: Double) {
    Column (
        modifier = Modifier
            .fillMaxHeight() // Make the Column fill the max height of the parent
            .border(width = 2.dp, color = Color.Red) // Add a red border to the Column
            .padding(horizontal = 50.dp) // Add horizontal padding inside the Column
    ) {
        timeSlice.entries.forEach { entry ->
            Box(
//                modifier = Modifier
//                    .border(2.dp, Color.Green)
            ) {
                if (entry is Note) {
                    NoteView(note = entry, staff = staff, lineSpacing = lineSpacing, false)
                }
            }
        }
    }
}