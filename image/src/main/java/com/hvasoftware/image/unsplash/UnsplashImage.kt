package com.hvasoftware.image.unsplash

data class UnsplashObject(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val promoted_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    // how to use this: https://androidrepo.com/repo/KingsMentor-BlurHashExt-android-images
    val blur_hash: String,
    val description: String,
    val alt_description: String,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    val liked_by_user: Boolean,
    val user: User
) {
}


data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val small_s3: String
) {

}


data class Links(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
) {

}


data class User(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val bio: String,
    val location: String,

    val links: UserLinks,
    val profile_image: UserProfileImage,

    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int
) {

}


data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String,
    val following: String,
    val followers: String
) {

}


data class UserProfileImage(
    val small: String,
    val medium: String,
    val large: String
) {

}




