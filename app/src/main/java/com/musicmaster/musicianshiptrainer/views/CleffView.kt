package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StaffType

@Composable
fun CleffView(score: Score, staff: Staff) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        val s = score.lineSpacing * 4.5
        if (staff.type == StaffType.TREBLE) {
            Text(" ")
            Text(
                text = String(Character.toChars(0x1D11E)),
                fontSize = s.sp
            )
        }
        else {
            val h = s * 0.2
            Spacer(modifier = Modifier.height(h.dp))
            Text (
                text = String(Character.toChars(0x1D122)),
                fontSize = (s * 1.1).sp
            )
        }
    }
}
