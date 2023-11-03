package com.musicmaster.musicianshiptrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicmaster.musicianshiptrainer.models.AccidentalType
import com.musicmaster.musicianshiptrainer.models.Key
import com.musicmaster.musicianshiptrainer.models.KeySignature
import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.ui.theme.MusicianshipTrainerTheme
import com.musicmaster.musicianshiptrainer.views.ScoreView
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StaffType
import com.musicmaster.musicianshiptrainer.models.TimeSignature

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicianshipTrainerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //ScoreView(score = Score())
                }
            }
        }
    }
}

@Preview
@Composable
fun ScoreViewPreview() {
    var keySig = KeySignature(AccidentalType.SHARP, "")
    val key = Key(keySig, Key.KeyType.MAJOR)
    val score = Score(key, timeSignature = TimeSignature(), linesPerStaff = 5)
    val timeSlice = score.createTimeSlice(1)
    timeSlice.addNote(Note(timeSlice, 60, 0.5))
    val timeSlice2 = score.createTimeSlice(2)
    timeSlice2.addNote(Note(timeSlice, 64, 2.0))
    val timeSlice3 = score.createTimeSlice(3)
    timeSlice3.addNote(Note(timeSlice,62, 1.0))
    score.createStaff(0, Staff(score, StaffType.Treble, 0, 5))
    score.createStaff(1, Staff(score, StaffType.Bass, 1, 5))
    ScoreView(score)
}

