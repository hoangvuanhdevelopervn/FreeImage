package com.hvasoftware.image.pexel

data class PexelVideoResponse(
    val videos: MutableList<PexelVideo>,
    val total_results: Int
) {


}