package com.hvasoftware.image.pexel

interface IPexelListVideo {

    fun onSuccess(pexelVideoResponse: PexelVideoResponse)

    fun onError(message: String)

}