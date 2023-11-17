package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicmaster.musicianshiptrainer.R
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.TimeSliceEntry

//@Composable
//fun RestQuarterImage(size: Double) {
//
//}

@Composable
fun RestView(staff: Staff, entry: TimeSliceEntry, lineSpacing: Double) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current // Getting the current context

        when (entry.value) {
            0.0 -> {
                Text(
                    text = "?",
                    fontSize = 24.sp, // equivalent to SwiftUI's .largeTitle
                    color = entry.getColor(staff)
                )
            }
            1.0 -> {
                Image(
                    painter = painterResource(id = R.drawable.rest_quarter_grayscale),
                    contentDescription = "",
                    modifier = Modifier
                        .height((lineSpacing * 3.0).dp)
                        .aspectRatio(1f)
                )
                //DisplayImage(size = context.dpToPx(lineSpacing))
//                Image(
//                    painter = painterResource(id = R.drawable.rest_quarter_grayscale),
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(entry.getColor(staff)),
//                    modifier = Modifier
//                        .height((lineSpacing * 3).dp)
//                        .aspectRatio(1f)
//                )
            }
            2.0 -> {
                val height = (lineSpacing / 2.0).dp
                Box(
                    modifier = Modifier
                        .width((lineSpacing * 1.5).dp)
                        .height(height)
                        .offset(y = -height / 2)
                        .background(entry.getColor(staff))
                )
            }
//            1.5 -> {
//                Row {
//                    Image(
//                        // Similar to the 1.0 case
//                    )
//                    Text(
//                        // Similar to the 0.0 case
//                    )
//                }
//            }
//            4.0 -> {
//                // Similar to the 2.0 case with different height and offset
//            }
//            0.5 -> {
//                // Similar to the 1.0 case but with different drawable
//            }
            else -> {
                Column {
                    // Similar to the 0.0 case
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
