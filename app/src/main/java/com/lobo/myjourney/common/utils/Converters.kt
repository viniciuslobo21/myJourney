package com.lobo.myjourney.common.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object Converters {
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    inline fun <reified T : Any> getJson(context: Context, jsonFileName: String): T {
        val jsonFileString = getJsonDataFromAsset(
            context,
            jsonFileName
        )
        return Gson().fromJson(jsonFileString, object : TypeToken<T>() {}.type)
    }
}