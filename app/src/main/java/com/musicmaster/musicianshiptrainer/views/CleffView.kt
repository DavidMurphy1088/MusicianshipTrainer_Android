package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StaffLayoutSize
import com.musicmaster.musicianshiptrainer.models.StaffType

@Composable
fun CleffView(staff: Staff, staffLayoutSize: StaffLayoutSize) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically // Align the children of the Row vertically in the center
    ) {
        //val result =
        val symbol = if (staff.staffType == StaffType.Treble) String(Character.toChars(0x1D11E)) else String(Character.toChars(0x1D122))
        val s = staffLayoutSize.lineSpacing * 4.5
        Text(
            text = symbol,
            fontSize = s.sp
        )
    }
}
