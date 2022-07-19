package com.hvasoftware.image.pexel


interface IPexelListImage {

    fun onSuccess(response: PexelImageResponse)

    fun onError(message: String)

}