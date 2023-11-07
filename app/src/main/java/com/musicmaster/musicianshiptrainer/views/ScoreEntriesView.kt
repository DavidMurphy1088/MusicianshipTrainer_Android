package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.TimeSlice

@Composable
fun ScoreEntriesView(score: Score, staff: Staff) {
    Row (
        modifier = Modifier.fillMaxWidth(), // Ensure the Row fills the maximum width
        horizontalArrangement = Arrangement.SpaceBetween // Space items evenly
     ) {
        score.scoreEntries.forEach { entry ->
            if (entry is TimeSlice) {
                Box (
                    //modifier = Modifier
                        //.width(childWidth) // Set the width of the Box
                        //.height(IntrinsicSize.Min) // Set the height to wrap content by default
                    modifier = Modifier.weight(1f, fill = true) // Assign equal weight to each Box

                )
                {
                    TimeSliceView(entry, staff, lineSpacing = score.lineSpacing)
                    StemView(score, staff, staff.noteLayoutPositions, entry.getTimeSliceNotes())
                }
            } else {
                // Handle non-TimeSlice entries here
            }
        }
    }
}
