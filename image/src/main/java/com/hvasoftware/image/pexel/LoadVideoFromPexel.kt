package com.hvasoftware.image.pexel

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject


class LoadVideoFromPexel(private val mContext: Context, val apiKey: String) {


    fun getListVideos(
        page: Int,
        callback: IPexelListVideo
    ) {
        val queue = Volley.newRequestQueue(mContext)
        val urlPexelVideo = "https://api.pexels.com/videos/popular?page=$page"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlPexelVideo, null, Response.Listener { response ->
                    callback.onSuccess(handleResponseVideo(response))
                },
                Response.ErrorListener { error ->
                    Log.wtf("getListVideos", "error: $error")
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

    fun searchListVideos(
        page: Int,
        query: String,
        callback: IPexelListVideo
    ) {
        val queue = Volley.newRequestQueue(mContext)
        //
        val urlPexelVideo = "https://api.pexels.com/videos/search?query=$query&page=$page"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlPexelVideo, null, Response.Listener { response ->
                    callback.onSuccess(handleResponseVideo(response))
                },
                Response.ErrorListener { error ->
                    Log.wtf("getListVideos", "error: $error")
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


    private fun handleResponseVideo(response: JSONObject): PexelVideoResponse {
        val listVideo: MutableList<PexelVideo> = arrayListOf()
        val videos = response.getJSONArray("videos")
        for (i in 0 until videos.length()) {
            val video = videos.getJSONObject(i)
            val data =
                Gson().fromJson(video.toString(), PexelVideo::class.java)
            listVideo.add(data)
        }
        val totalResults = response.getInt("total_results")
        return PexelVideoResponse(listVideo, totalResults)
    }
}