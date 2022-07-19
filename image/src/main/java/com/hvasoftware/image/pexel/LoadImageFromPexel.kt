package com.hvasoftware.image.pexel

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.image.Constants
import org.json.JSONException
import org.json.JSONObject


class LoadImageFromPexel(private val mContext: Context, val apiKey: String) {

    fun getListImages(
        page: Int,
        callback: IPexelListImage
    ) {
        val queue = Volley.newRequestQueue(mContext)
        val urlPexel =
            "https://api.pexels.com/v1/curated?page=$page&per_page=${Constants.LIMIT_DEFAULT}"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Method.GET, urlPexel, null, Response.Listener { response ->
                callback.onSuccess(handlePexelResponse(response))
            },
                Response.ErrorListener { error ->
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                    callback.onError(error.localizedMessage?.toString() ?: "Error")
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = apiKey
                    return headers
                }
            }
        queue.add(jsonObjectRequest)
    }


    fun detailImage(
        idImage: String,
        callback: IPexelDetailImage
    ) {
        if (idImage.isBlank()) {
            callback.onError("id invalid")
            return
        }
        val queue = Volley.newRequestQueue(mContext)
        val urlPexel = "https://api.pexels.com/v1/photos/$idImage"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Method.GET, urlPexel, null,
                Response.Listener { response ->
                    val data = Gson().fromJson(response.toString(), PexelImage::class.java)
                    callback.onSuccess(data)
                },
                Response.ErrorListener { error ->
                    callback.onError(error.message.toString())
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] =
                        apiKey
                    return headers
                }
            }
        queue.add(jsonObjectRequest)
    }


    fun searchImage(
        page: Int,
        query: String,
        callback: IPexelListImage
    ) {
        val queue = Volley.newRequestQueue(mContext)
        val urlPexel =
            "https://api.pexels.com/v1/search/?page=$page&per_page=${Constants.LIMIT_DEFAULT}&query=$query"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Method.GET, urlPexel, null,
                Response.Listener { response ->
                    callback.onSuccess(handlePexelResponse(response))
                },
                Response.ErrorListener { error ->
                    callback.onError(error.message.toString())
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] =
                        apiKey
                    return headers
                }
            }
        queue.add(jsonObjectRequest)
    }

    private fun handlePexelResponse(response: JSONObject): PexelImageResponse {
        val images: MutableList<PexelImage> = arrayListOf()
        val photos = response.getJSONArray("photos")
        val totalResults = response.getInt("total_results")
        for (i in 0 until photos.length()) {
            try {
                val responseObject = photos.getJSONObject(i)
                val data =
                    Gson().fromJson(responseObject.toString(), PexelImage::class.java)
                images.add(data)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return PexelImageResponse(images, totalResults)
    }


}