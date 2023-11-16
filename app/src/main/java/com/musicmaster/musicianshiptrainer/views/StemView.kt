package com.musicmaster.musicianshiptrainer.views
import android.content.Context
import androidx.compose.foundation.Canvas

import androidx.compose.foundation.layout.BoxWithConstraints

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext

import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.models.NoteLayoutPositions
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StemDirection


fun Context.dpToPx(dp: Double): Double {
    return dp * resources.displayMetrics.density
}

@Composable
fun StemView(
    score: Score,
    staff: Staff,
    notePositionLayout: NoteLayoutPositions,
    notes: List<Note>
) {
    val context = LocalContext.current // Getting the current context

    fun findStemLength(): Double {
        var len = 0.0
        if (notes.isNotEmpty()) {
            len = notes[0].stemLength * score.lineSpacing
        }
        return context.dpToPx(len)
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
            val staffNotes = getStaffNotes(staff)
            if (staffNotes.isNotEmpty()) {
                if (staffNotes.size <= 1) {
                    val startNote = staffNotes.first().getBeamStartNote(score, notePositionLayout)
                    if (startNote.value != Note.VALUE_WHOLE) {
                        val stemDirection = if (startNote.stemDirection == StemDirection.UP) -1.0 else 1.0
                        val midX = size.width / 2 + midPointXOffset(notes, staff, stemDirection).toFloat()
                        val midY = size.height / 2
                        var offsetY = (startNote.noteStaffPlacements[staff.staffNum].offsetFromStaffMidline) * 0.5 * score.lineSpacing
                        offsetY = context.dpToPx(offsetY)
                        val path = Path().apply {
                            moveTo(midX, (midY - offsetY).toFloat())
                            lineTo(midX, (midY - offsetY + (stemDirection * findStemLength())).toFloat())
                        }
                        drawPath(path, startNote.getColor(staff), style = Stroke(width = 3.0f))
                    }
                } else {
                    // Logic for drawing stems for chords goes here
                }
            }
        }
    }
}

