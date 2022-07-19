package com.hvasoftware.image.pexel



interface IPexelDetailImage {

    fun onSuccess(image: PexelImage)

    fun onError(message: String)

}