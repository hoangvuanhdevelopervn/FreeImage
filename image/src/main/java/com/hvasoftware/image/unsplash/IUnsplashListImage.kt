package com.hvasoftware.image.unsplash

import com.hvasoftware.image.unsplash.UnsplashObject

interface IUnsplashListImage {

    fun onSuccess(images: MutableList<UnsplashObject>)

    fun onError(message: String)

}