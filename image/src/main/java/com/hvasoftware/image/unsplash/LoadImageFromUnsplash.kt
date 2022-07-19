package com.hvasoftware.image.unsplash

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.image.Constants
import org.json.JSONException


class LoadImageFromUnsplash(private val mContext: Context, private val clientId: String) {


    fun getRandomListImage(callback: IUnsplashListImage) {
        val queue = Volley.newRequestQueue(mContext)
        val images: MutableList<UnsplashObject> = arrayListOf()
        val url =
            "https://api.unsplash.com/photos/random?count=${Constants.LIMIT_DEFAULT}&client_id=$clientId"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    try {
                        val responseObject = response.getJSONObject(i)
                        val data =
                            Gson().fromJson(responseObject.toString(), UnsplashObject::class.java)
                        images.add(data)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                callback.onSuccess(images)
            }
        ) { error ->
            callback.onError(error.localizedMessage?.toString() ?: "Something when wrong")
        }
        queue.add(jsonArrayRequest)
    }

    fun getListImageNormal(page: Int, callback: IUnsplashListImage) {
        if (page < 0) {
            callback.onError("Invalid page value")
            return
        }
        val queue = Volley.newRequestQueue(mContext)
        val images: MutableList<UnsplashObject> = arrayListOf()
        val url =
            "https://api.unsplash.com/photos?page=$page&per_page=${Constants.LIMIT_DEFAULT}&client_id=$clientId"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    try {
                        val responseObject = response.getJSONObject(i)
                        val data =
                            Gson().fromJson(responseObject.toString(), UnsplashObject::class.java)
                        images.add(data)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                callback.onSuccess(images)
            }
        ) { error ->
            callback.onError(error.localizedMessage?.toString() ?: "Something when wrong")
        }
        queue.add(jsonArrayRequest)
    }


    // https://api.unsplash.com/search/photos?page=1&query=office&client_id=
    fun searchListImage(page: Int, query: String, callback: IUnsplashListImage) {
        if (query.isBlank()) {
            callback.onError("Invalid query value")
            return
        }
        val queue = Volley.newRequestQueue(mContext)
        val images: MutableList<UnsplashObject> = arrayListOf()
        val url =
            "https://api.unsplash.com/search/photos?page=$page&per_page=${Constants.LIMIT_DEFAULT}&query=$query&client_id=$clientId"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val results = response.getJSONArray("results")
                for (i in 0 until results.length()) {
                    try {
                        val responseObj = results.getJSONObject(i)
                        val data =
                            Gson().fromJson(responseObj.toString(), UnsplashObject::class.java)
                        images.add(data)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                callback.onSuccess(images)
            },
            { error ->
                callback.onError(error.localizedMessage?.toString() ?: "Something when wrong")
            }
        )
        queue.add(jsonObjectRequest)
    }


}