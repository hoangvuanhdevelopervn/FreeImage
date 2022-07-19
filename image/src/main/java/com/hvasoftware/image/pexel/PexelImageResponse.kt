package com.hvasoftware.image.pexel


data class PexelImageResponse(
    val photos: MutableList<PexelImage>,
    val total_results: Int
) {

}