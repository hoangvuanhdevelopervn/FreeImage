package com.hvasoftware.image.pexel

data class PexelVideo(
    val id: Int,
    val width: Int,
    val height: Int,
    val duration: Int,


    val url: String,
    val image: String,
    val user: User,

    val video_files: MutableList<VideoFiles>,
    val video_pictures: MutableList<VideoPictures>,
) {
}


data class VideoFiles(
    val id: Int,
    val quality: String,
    val file_type: String,
    val width: Int,
    val height: Int,
    val link: String
) {
}


data class VideoPictures(
    val id: Int,
    val nr: Int,
    val picture: String
) {
}


data class User(
    val id: Int,
    val name: String,
    val url: String
) {
}