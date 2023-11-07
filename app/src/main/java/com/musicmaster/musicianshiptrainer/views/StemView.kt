
package com.musicmaster.musicianshiptrainer.views
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.models.NoteLayoutPositions
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StaffType
import com.musicmaster.musicianshiptrainer.models.StemDirection

@Composable
fun StemView1(score: Score) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.border(2.dp, Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("S")
    }
}
@Composable
fun StemView(
    score: Score,
    staff: Staff,
    notePositionLayout: NoteLayoutPositions,
    notes: List<Note>
) {
    // Converted SwiftUI functions to Kotlin functions
    fun findStemLength(): Double {
        var len = 0.0
        if (notes.isNotEmpty()) {
            len = notes[0].stemLength * score.lineSpacing
        }
        return len
    }

    fun getNoteWidth(): Double {
        return score.lineSpacing * 1.2
    }

    fun midPointXOffset(
        notes: List<Note>,
        staff: Staff,
        stemDirection: Double
    ): Double {
        for (n in notes) {
            if (n.rotated) {
                if (n.midiNumber < staff.middleNoteValue) {
                    return -1.0 * getNoteWidth()
                }
            }
        }
        return stemDirection * -1.0 * getNoteWidth()
    }

    fun getStaffNotes(staff: Staff): List<Note> {
        return notes.filter { it.staffNum == staff.staffNum }
    }

    // Translating the body of the SwiftUI view to Composable function
    BoxWithConstraints {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var staffNotes = getStaffNotes(staff)
            if (staffNotes.isNotEmpty()) {
                if (staffNotes.size <= 1) {
                    val startNote = staffNotes.first().getBeamStartNote(score, notePositionLayout)
                    if (startNote.value != Note.VALUE_WHOLE) {
                        val stemDirection = if (startNote.stemDirection == StemDirection.UP) -1.0 else 1.0
                        val midX = size.width / 2 + midPointXOffset(notes, staff, stemDirection).toFloat()
                        val midY = size.height / 2
                        val offsetY = (startNote.noteStaffPlacements[staff.staffNum].offsetFromStaffMidline) * 0.5 * score.lineSpacing
                        val path = Path().apply {
                            moveTo(midX, (midY - offsetY).toFloat())
                            lineTo(midX, (midY - offsetY + (stemDirection * findStemLength())).toFloat())
                        }
                        drawPath(path, startNote.getColor(staff), style = Stroke(width = 1.5f))
                    }
                } else {
                    // Logic for drawing stems for chords goes here
                }
            }
        }
    }
}

