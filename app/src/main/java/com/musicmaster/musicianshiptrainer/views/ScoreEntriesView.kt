package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.TimeSlice

@Composable
fun ScoreEntriesView(score: Score, staff: Staff) {
    Row(
        modifier = Modifier
            .fillMaxHeight() // Make the Row fill the max height of parent
            .border(width = 0.dp, color = Color.Green),
        verticalAlignment = Alignment.CenterVertically

//        modifier = Modifier
//            .fillMaxWidth()
//            .align(Alignment.Center)
//            //.size(height.dp)
    ) {
        score.scoreEntries.forEach { entry ->
            if (entry is TimeSlice) {
                TimeSliceView(entry, staff, lineSpacing = score.staffLayoutSize.lineSpacing)
            } else {
                // Handle non-TimeSlice entries here
            }
        }
    }
}
