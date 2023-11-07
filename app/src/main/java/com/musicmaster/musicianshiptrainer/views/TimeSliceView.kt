package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
            .fillMaxSize(),
            //.border(1.dp, Color.Green),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        timeSlice.entries.forEach { entry ->
            Box(
            ) {
                if (entry is Note) {
                    NoteView(note = entry, staff = staff, lineSpacing = lineSpacing, false)
                }
            }
        }
    }
}

