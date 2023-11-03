package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
        modifier = Modifier,
//            .size(100.dp)
//            .fillMaxWidth(),
//            .background(Color.Blue)
//            .padding(end = 16.dp).

        horizontalArrangement = Arrangement.spacedBy(30.dp)
        //verticalAlignment = Alignment.CenterVertically
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
