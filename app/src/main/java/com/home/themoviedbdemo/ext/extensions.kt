package com.home.themoviedbdemo.ext

import android.util.Log
import com.github.kittinunf.fuel.core.Request
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

fun Request.log(): Request {
    response { request, _, _ ->
        Log.d("HTTP", request.toString())
    }
    return this
}

/**
 * 從原json資料中, 篩選出results的資料, 並以JsonArray的格式返回
 */
fun String.getResultJson(): JsonArray {
    val json = Gson().fromJson(this, JsonObject::class.java)
    return json.get("results").asJsonArray
}