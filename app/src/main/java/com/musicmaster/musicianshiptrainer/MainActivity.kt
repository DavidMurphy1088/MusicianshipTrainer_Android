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
import com.musicmaster.musicianshiptrainer.ui.theme.MusicianshipTrainerTheme
import com.musicmaster.musicianshiptrainer.views.ScoreView
import com.musicmaster.musicianshiptrainer.models.Score

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
                    ScoreView(score = Score())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScorePreview() {
    MusicianshipTrainerTheme {
        ScoreView(score = Score())
    }
}