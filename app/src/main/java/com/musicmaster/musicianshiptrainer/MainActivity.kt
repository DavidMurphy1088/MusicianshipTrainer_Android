package com.musicmaster.musicianshiptrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.musicmaster.musicianshiptrainer.models.AccidentalType
import com.musicmaster.musicianshiptrainer.models.Key
import com.musicmaster.musicianshiptrainer.models.KeySignature
import com.musicmaster.musicianshiptrainer.models.Note
import com.musicmaster.musicianshiptrainer.models.Rest
import com.musicmaster.musicianshiptrainer.ui.theme.MusicianshipTrainerTheme
import com.musicmaster.musicianshiptrainer.views.ScoreView
import com.musicmaster.musicianshiptrainer.models.Score
import com.musicmaster.musicianshiptrainer.models.Staff
import com.musicmaster.musicianshiptrainer.models.StaffType
import com.musicmaster.musicianshiptrainer.models.TimeSignature
import com.musicmaster.musicianshiptrainer.data.GoogleAPI
import android.app.Application
import android.content.Context

@Preview
@Composable
fun ScoreViewPreview() {
    val keySig = KeySignature(accidentalType = AccidentalType.SHARP, keyName = "")
    val key = Key(keySig, Key.KeyType.MAJOR)
    val score = Score(key, timeSignature = TimeSignature(), linesPerStaff = 5)
    val s1 = Staff(score, StaffType.TREBLE, 0, 5)
    score.createStaff(0, s1)

    val numbers = arrayOf(62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79)
    //val numbers = arrayOf(71)
    val g = GoogleAPI.shared
    g.getContentSheet("X")
    //GoogleAPI.shared.getContentSheet("MySheetName")
    //GoogleAPI.shared.getContentSheet(sheetName = "sheet", onDone = ())
//    GoogleAPI.shared.getContentSheet("MySheetName") { status, data ->
//        when (status) {
//            RequestStatus.SUCCESS -> {
//                // Handle the success case
//                println("Data received successfully.")
//                val dataString = data?.let { String(it, Charsets.UTF_8) }
//                println("Received data: $dataString")
//            }
//            RequestStatus.WAITING -> {
//                // Handle the waiting case
//                println("Waiting for data...")
//            }
//            RequestStatus.FAILED -> {
//                // Handle the failure case
//                println("Failed to get data.")
//            }
//        }
//    }
    numbers.forEachIndexed { index, midi ->
        var timeSlice = score.createTimeSlice()
        timeSlice.addNote(Note(timeSlice,midi, 1.0))
        if (index % 3 == 2) {
            timeSlice = score.createTimeSlice()
            timeSlice.addRest(Rest(timeSlice = timeSlice, value = 1.0))
            score.addBarLine()
        }
    }
    ScoreView(score)
}

class MusicianshipTrainerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        private lateinit var instance: MusicianshipTrainerApp
        fun getAppContext(): Context = instance.applicationContext
    }
}




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
                    ScoreViewPreview()
                }
            }
        }
    }
}

