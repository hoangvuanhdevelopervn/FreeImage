# FreeImage



FreeImage is a library that helps you get free photos and videos from Unsplash and Pexel

- Load list image on Pexel
- Search image on Pexel
- Load list image on Unsplash
- Search image on Unsplash
- Load list video popular on Pexel
- Search list video on Pexel


# How to

To get a Git project into your build:

**Step 1.**  Add the JitPack repository to your build file

-   [gradle](https://jitpack.io/#gradle)
-   [maven](https://jitpack.io/#maven)
-   [sbt](https://jitpack.io/#sbt)
-   [leiningen](https://jitpack.io/#lein)

Add it in your root build.gradle at the end of repositories:

```css
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.**  Add the dependency

```css
	dependencies {
	        implementation 'com.github.hoangvuanhdevelopervn:FreeImage:Tag'
	}

```


**Step 3. Fix error.**  Add this code in AndroidManifest.xml

```css
	tools:replace="android:icon,android:roundIcon,android:theme"
```

# Usage

```kotlin
LoadImageFromPexel(this, pexelKey).getListImages(1, object : IPexelListImage {  
    override fun onSuccess(response: PexelImageResponse) {  
       // handle data here     
    }  
  
    override fun onError(message: String) {  
       // handle error here     
    }  
  
})


LoadVideoFromPexel(this, pexelKey).getListVideos(1, object : IPexelListVideo {  
    override fun onSuccess(pexelVideoResponse: PexelVideoResponse) {  
        Log.wtf("getListVideoPexel", "video: $pexelVideoResponse")  
    }  
  
    override fun onError(message: String) {  
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()  
    }  
})



LoadImageFromUnsplash(this, unsplashKey).getListImageNormal(1, object : IUnsplashListImage {  
    override fun onSuccess(images: MutableList<UnsplashObject>) {  
        Log.wtf("LoadImageFromUnsplash", "images: ${images.size}")   
    }  
  
    override fun onError(message: String) {  
        Log.wtf("LoadImageFromUnsplash", "onError: $message")  
    }  
})


```


Share this release:

[![](https://jitpack.io/v/hoangvuanhdevelopervn/FreeImage.svg)](https://jitpack.io/#)

[Link](https://jitpack.io/#hoangvuanhdevelopervn/FreeImage/Tag)

**That's it!**  The first time you request a project JitPack checks out the code, builds it and serves the build artifacts (jar, aar).

If the project doesn't have any  [GitHub Releases](https://github.com/blog/1547-release-your-software)  you can use the short commit hash or 'master-SNAPSHOT' as the version.
