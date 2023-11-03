package com.musicmaster.musicianshiptrainer.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.models.Staff

@Composable
fun NoteView(note: Note, staff: Staff, lineSpacing:Double, inError:Boolean) { //}, noteFrameWidth: Dp, geometry: Size, inError: Boolean) {
    val placement = note.noteStaffPlacements[staff.staffNum]
    val offsetFromStaffMiddle = placement.offsetFromStaffMidline
    val accidental = placement.accidental
    //val noteEllipseMidpoint = geometry.height / 2f - offsetFromStaffMiddle * lineSpacing / 2f
    //val noteEllipseMidpoint = 50 / 2f - offsetFromStaffMiddle * lineSpacing / 2f
    val noteValueUnDotted = if (note.isDotted()) note.value * 2f / 3f else note.value
    val magic = 2.3
    val noteHeight = lineSpacing * magic
    val noteWidth = noteHeight * 1.2
    //val xoffset = (noteWidth / 2.0) - (if (note.rotated) noteWidth else 0.0)

    Box(
//
    )
    {
        if (inError) {
             Text(
                text = "X",
                color = Color.Red,
                fontSize = (lineSpacing * 3).sp,
                fontWeight = FontWeight.Bold
                //modifier = Modifier.align(Alignment.Center).offset(x = xoffset.dp, y = noteEllipseMidpoint.dp)
            )
        } else {
            if (note.staffNum == staff.staffNum) {
                // Equivalent of NoteHiliteView can go here
            }

//            accidental?.let {
//                val yOffset = if (accidental == 1) lineSpacing / 5 else 0f
//                Text(
//                    text = getAccidental(accidental),
//                    fontSize = lineSpacing * 3.sp,
//                    color = note.getColor(staff),
//                    modifier = Modifier
//                        .size(width = noteWidth.dp, height = (lineSpacing * 1f).dp)
//                        .align(Alignment.Center)
//                        .offset(
//                            x = (noteFrameWidth / 2 - lineSpacing * if (timeSlice.anyNotesRotated()) 3f else 2f).dp,
//                            y = (noteEllipseMidpoint + yOffset).dp
//                        )
//                )
//            }

            if (noteValueUnDotted in listOf(Note.VALUE_QUARTER, Note.VALUE_QUAVER, Note.VALUE_SEMIQUAVER, Note.VALUE_HALF, Note.VALUE_WHOLE)) {
                // Draw the filled ellipse
                //Canvas(modifier = Modifier.align(Alignment.Center).offset(x = xoffset.dp, y = noteEllipseMidpoint.dp)) {
                Canvas(
                    modifier = Modifier.fillMaxHeight() // Required so the size calcs below can occur
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val topLeftX = (canvasWidth - noteWidth) / 2
                    val topLeftY = (canvasHeight - noteHeight) / 2 - (offsetFromStaffMiddle * lineSpacing * magic) / 2.0
                    if (noteValueUnDotted in listOf(Note.VALUE_HALF, Note.VALUE_WHOLE)) {
                        drawOval(
                            style = Stroke(width = 4f),
                            color = note.getColor(staff),
                            topLeft = Offset(
                                topLeftX.toFloat(),
                                topLeftY.toFloat()
                            ), // Use the calculated top-left position
                            size = Size(noteWidth.toFloat(), noteHeight.toFloat())
                        )

                    }
                    else {
                        drawOval(
                            color = note.getColor(staff),
                            topLeft = Offset(
                                topLeftX.toFloat(),
                                topLeftY.toFloat()
                            ), // Use the calculated top-left position
                            size = Size(noteWidth.toFloat(), noteHeight.toFloat())
                        )
                    }
                }
            }

//            if (noteValueUnDotted in listOf(Note.VALUE_HALF, Note.VALUE_WHOLE)) {
//                // Draw the outlined ellipse
//                val offset = if (note.rotated) noteWidth else 0
//                Canvas(modifier = Modifier.align(Alignment.Center).offset(x = xoffset.dp, y = noteEllipseMidpoint.dp)) {
//                    drawOval(
//                        color = note.getColor(staff),
//                        style = Stroke(width = 2f),
//                        size = Size(noteWidth.toFloat(), ((lineSpacing * 0.9f).toFloat()))
//                    )
//                }
//            }

//            if (note.isDotted()) {
//                // Draw the dot
//                val yOffset:Double = if (offsetFromStaffMiddle % 2 == 0) lineSpacing / 3f else 0.0
//                val y = noteEllipseMidpoint - yOffset
//                Canvas(modifier = Modifier.align(Alignment.Center).offset(x = (noteWidth / 2 + noteWidth / 1.1).dp, y = y.dp)) {
//                    drawOval(
//                        color = note.getColor(staff),
//                        size = Size(noteWidth.toFloat() / 3f, noteWidth.toFloat() / 3f)
//                    )
//                }
//            }

//            if (!note.isOnlyRhythmNote) {
//                // Ledger lines
//                getLedgerLines(staff, note, noteWidth, lineSpacing).forEach { line ->
//                    val y = geometry.height / 2f + line.offsetVertical
//                    val x = noteFrameWidth / 2 - noteWidth - if (note.rotated) noteWidth else 0
//                    // Draw ledger lines
//                    Canvas(modifier = Modifier.align(Alignment.Center)) {
//                        drawLine(
//                            color = note.getColor(staff),
//                            start = Offset(x, y),
//                            end = Offset(x + 2 * noteWidth, y),
//                            strokeWidth = 1.dp.toPx()
//                        )
//                    }
//                }
//            }
        }
    }
}
