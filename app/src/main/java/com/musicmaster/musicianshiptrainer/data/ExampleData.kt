package com.musicmaster.musicianshiptrainer.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class ExampleData private constructor() : ViewModel() {
    companion object {
        val sharedExampleData = ExampleData()
    }

    private val googleAPI = GoogleAPI.shared
    var dataStatus = MutableLiveData<RequestStatus>().apply { value = RequestStatus.WAITING }

    init {
        loadData()
    }

    private fun loadData() {
        // Assuming MusicianshipTrainerApp.root.subSections is mutable and accessible
        //MusicianshipTrainerApp.root.subSections.clear()
        //val sheetName = if (Settings.shared.useTestData) "ContentSheetID_TEST" else "ContentSheetID"
        val sheetName = "ContentSheetID_TEST"
        data class JSONSheet(
            val range: String,
            val values: List<List<String>>
        )
//        googleAPI.getContentSheet(sheetName) { status, data ->
//            CoroutineScope(Dispatchers.Main).launch {
//                when (status) {
//                    RequestStatus.SUCCESS -> {
//                        data?.let {
//                            try {
//                                val jsonString = String(data, Charsets.UTF_8)
//                                val dataObject = Json.decodeFromString<JSONSheet>(jsonString)
//                                dataStatus.value = RequestStatus.SUCCESS
//                            } catch (e: Exception) {
//                                // Logging error
//                            }
//                        } ?: run {
//                            dataStatus.value = RequestStatus.FAILED
//                            // Log "No content data"
//                        }
//                    }
//                    else -> {
//                        dataStatus.value = status
//                    }
//                }
//            }
//        }

        // Similar structure for the second call to getContentSheet
        // ...
    }

    private fun setDataReady(way: RequestStatus) {
        dataStatus.value = way
    }

    // Define other methods like loadSheetData, loadMelodies, etc.
}

// Assuming a JSONSheet data class is defined to parse the JSON data
data class JSONSheet(
    val range: String,
    val values: List<List<String>>
)
