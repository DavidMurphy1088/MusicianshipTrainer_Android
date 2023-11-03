package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff

@Composable
fun StaffView(score: Score, staff: Staff) {
    val staffLayoutSize = score.staffLayoutSize
    val height = staffLayoutSize.lineSpacing * 12
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.align(Alignment.Center)
            .size(height.dp)

    ) {
        Row(
            //modifier = Modifier.align(Alignment.Center)
        ) {
            CleffView(staff, score.staffLayoutSize)
            ScoreEntriesView(score, staff)
        }
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            for (i in 1..5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Black)
                )
                if (i < 5) {
                    val s = score.staffLayoutSize.lineSpacing * 1.0
                    Spacer(modifier = androidx.compose.ui.Modifier.height(s.dp)) // Spacing between lines
                }
            }
        }
    }
}
