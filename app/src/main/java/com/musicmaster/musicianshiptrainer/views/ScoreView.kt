package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.musicmaster.musicianshiptrainer.models.Score

@Composable
fun ScoreView(score: Score) {
    Column {
        Text(text = "___SCORE____")
//        score.staffs.forEachIndexed { index, staff ->
//            val s = score.staffLayoutSize.lineSpacing * 5
//            Spacer(modifier = Modifier.height(s.dp))
//            StaffView(score, staff)
        //}
    }
}
