package com.musicmaster.musicianshiptrainer.data

import com.musicmaster.musicianshiptrainer.MusicianshipTrainerApp
import com.musicmaster.musicianshiptrainer.models.Logger
import com.musicmaster.musicianshiptrainer.R
//The R class in Android is an auto-generated class that acts as a reference to all resources in your app.
// When you import R from your application's package, it doesn't refer directly to a specific XML file like GoogleAPI.xml

enum class OAuthCallType {
    FILE,
    FILES_IN_FOLDER,
    GOOGLE_DOC
}

enum class RequestStatus {
    SUCCESS,
    WAITING,
    FAILED
}

class DataRequest(
    var callType: OAuthCallType,
    var id: String,
    var context: String,
    var targetExampleKey: String? = null,
    var url: String? = null
)

class DataCacheEntry(
    val wasLoadedFromExternal: Boolean,
    val data: ByteArray?
)

class DataCache() {
    private var dataCache: MutableMap<String, DataCacheEntry> = mutableMapOf()
    private val enabled = true

    fun showCaches() {
        println("\n----CACHES")
        dataCache.forEach { (key, value) ->
            println("  Cache - Key: $key, Value: $value")
        }
    }

    fun getCacheEntry(key: String): DataCacheEntry? = dataCache[key]

    fun hasCacheKey(key: String): Boolean = dataCache.containsKey(key)

//    fun getData(key: String): ByteArray? {
//        if (!enabled) return null
//        return dataCache[key]?.data ?: run {
//            val data = sharedPreferences.getString(key, null)?.toByteArray()
//            dataCache[key] = DataCacheEntry(wasLoadedFromExternal = false, data = data)
//            data
//        }
//    }

//    fun setFromExternalData(key: String, data: ByteArray?) {
//        dataCache[key] = DataCacheEntry(wasLoadedFromExternal = true, data = data)
//        sharedPreferences.edit().putString(key, data?.let { String(it) }).apply()
//    }
}

class GoogleAPI private constructor() {
    companion object {
        val shared = GoogleAPI()
    }
    val logger = Logger.instance
    val dataCache = DataCache(/* pass SharedPreferences instance here */)
    var accessToken: String? = null

    fun getContentSheet(sheetName: String) {
        val ctx = MusicianshipTrainerApp.getAppContext()
        val xxx = ctx.getString(R.string.APIKey)
        println(xxx)
    }

    fun getContentSheet1(sheetName: String, onDone: (status: RequestStatus, data: ByteArray?) -> Unit) {
        val x = MusicianshipTrainerApp.getAppContext().getString(R.string.APIKey)
        //val xx = MusicianshipTrainerApp.getContext().getString(R.string.ContentSheetID_TEST)
        val sheetKey: String = MusicianshipTrainerApp.getAppContext().getString(R.string.ContentSheetID_TEST) //getAPIBundleData(key = sheetName)

        if (sheetKey != null) {
            val request = DataRequest(
                callType = OAuthCallType.FILE,
                id = sheetKey,
                context = "getExampleSheet",
                targetExampleKey = null
            )

            var url = "https://sheets.googleapis.com/v4/spreadsheets/${request.id}/values/Sheet1"
            request.url = url
//            getByAPI(request = request) { status, data ->
//                onDone(RequestStatus.SUCCESS, data)
//            }
        } else {
            logger.reportError("Cannot find example sheet id")
            onDone(RequestStatus.FAILED, null)
        }
    }
}
