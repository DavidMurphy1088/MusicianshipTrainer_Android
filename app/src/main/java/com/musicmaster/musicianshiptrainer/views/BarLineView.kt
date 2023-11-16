package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.ScoreEntry
import com.musicmaster.musicianshiptrainer.models.Staff

@Composable
fun BarLineView(score: Score, entry: ScoreEntry, staff: Staff) {
    val lineSpacing = score.lineSpacing
    val rectangleWidth = 1.dp
    val rectangleHeight = (4 * lineSpacing).dp

    Box(modifier = Modifier
        .width((lineSpacing * 1.1).dp)
        .fillMaxHeight()
        .wrapContentSize(Alignment.Center)
    ) {
        Canvas(modifier = Modifier.size(rectangleWidth, rectangleHeight)) {
            drawRect(
                color = Color.Black,
                size = Size(rectangleWidth.toPx(), rectangleHeight.toPx())
            )
        }
    }
}