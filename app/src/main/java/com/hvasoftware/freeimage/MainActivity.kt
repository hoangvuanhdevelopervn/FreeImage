package com.hvasoftware.freeimage

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hvasoftware.freeimage.databinding.ActivityMainBinding
import com.hvasoftware.image.pexel.*
import com.hvasoftware.image.unsplash.IUnsplashListImage
import com.hvasoftware.image.unsplash.LoadImageFromUnsplash
import com.hvasoftware.image.unsplash.UnsplashObject


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var mAdapterImage: AdapterImage
    private val mListImage: MutableList<Image> = arrayListOf()
    private val pexelKey = "563492ad6f917000010000012dafc9fe2de943f780040ffe4bacd535"
    private val unsplashKey = "c77b4131428f8d105981c6152b68f88e964815d8c0ba989229d7a027781396f5"
    private var mTypeSearch = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        setRvImage()

        setUpSearch()

        getListImagePexel()

    }

    private fun setUpSearch() {
        binding.edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val mContent = binding.edtSearch.text.toString().trim()
                if (mContent.isBlank()) {
                    Toast.makeText(this, "Content is empty", Toast.LENGTH_SHORT).show()
                } else {
                    binding.pbLoading.visibility = View.VISIBLE
                    if (mTypeSearch == 0) {
                        startLoadData()
                        searchImagesPexel(mContent)
                    } else {
                        startLoadData()
                        searchImageUnsplash(mContent)
                    }
                }
                return@OnEditorActionListener true
            }
            false
        })


    }

    private fun setupSpinner() {

        val items = arrayOf(
            "GET LIST IMAGE FROM PEXEL", // 0
            "SEARCH IMAGE FROM PEXEL", // 1
            "GET RANDOM LIST IMAGE FROM UNSPLASH",  // 2
            "GET LIST IMAGE FROM UNSPLASH", // 3
            "SEARCH LIST IMAGE FROM UNSPLASH" // 4
        )

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.mySpinner.adapter = adapterSpinner
        binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        binding.edtSearch.visibility = View.GONE
                        startLoadData()
                        getListImagePexel()
                    }
                    1 -> {
                        binding.edtSearch.visibility = View.VISIBLE
                        mTypeSearch = 0
                    }
                    2 -> {
                        binding.edtSearch.visibility = View.GONE
                        startLoadData()
                        getRandomListImageFromUnsplash()
                    }
                    3 -> {
                        binding.edtSearch.visibility = View.GONE
                        startLoadData()
                        getListImageFromUnsplash()
                    }
                    4 -> {
                        binding.edtSearch.visibility = View.VISIBLE
                        mTypeSearch = 1
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    private fun startLoadData() {
        mListImage.clear()
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun setRvImage() {
        mAdapterImage = AdapterImage(this, itemClickedListener = {

        })
        binding.rvImage.adapter = mAdapterImage
    }

    private fun dummyData() {
        binding.pbLoading.visibility = View.GONE
        mAdapterImage.setData(mListImage)
    }


    private fun getListImagePexel() {
        LoadImageFromPexel(this, pexelKey).getListImages(1, object : IPexelListImage {
            override fun onSuccess(response: PexelImageResponse) {
                //  Log.wtf("getListImages", "response: $response")
                for (item in response.photos) {
                    mListImage.add(Image(item.id.toString(), item.src.medium))
                }
                dummyData()
            }

            override fun onError(message: String) {
                binding.pbLoading.visibility = View.GONE
                Log.wtf("getListImagePexel", "onError: $message")
            }

        })
    }


    private fun searchImagesPexel(query: String) {
        LoadImageFromPexel(
            this,
            pexelKey
        ).searchImage(1, query, object : IPexelListImage {
            override fun onSuccess(response: PexelImageResponse) {
                Log.wtf("detailImage", "response: $response")
                for (item in response.photos) {
                    mListImage.add(Image(item.id.toString(), item.src.medium))
                }
                dummyData()
            }

            override fun onError(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun detailImagePexel() {
        LoadImageFromPexel(this, "").detailImage("", object : IPexelDetailImage {
            override fun onSuccess(image: PexelImage) {
                Log.wtf("detailImage", "image: $image")
            }

            override fun onError(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getListVideoPexel() {
        LoadVideoFromPexel(this, pexelKey).getListVideos(1, object : IPexelListVideo {
            override fun onSuccess(pexelVideoResponse: PexelVideoResponse) {
                Log.wtf("getListVideoPexel", "video: $pexelVideoResponse")
            }


            override fun onError(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchListVideoPexel(query: String) {
        LoadVideoFromPexel(this, pexelKey).searchListVideos(1, query, object : IPexelListVideo {
            override fun onSuccess(pexelVideoResponse: PexelVideoResponse) {
                Log.wtf("searchListVideoPexel", "videos: $pexelVideoResponse")
            }


            override fun onError(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getRandomListImageFromUnsplash() {
        LoadImageFromUnsplash(
            this,
            unsplashKey
        ).getRandomListImage(object : IUnsplashListImage {
            override fun onSuccess(images: MutableList<UnsplashObject>) {
                Log.wtf("LoadImageFromUnsplash", "images: ${images.size}")
                for (item in images) {
                    mListImage.add(Image(item.id, item.urls.small))
                }
                dummyData()
            }

            override fun onError(message: String) {
                Log.wtf("LoadImageFromUnsplash", "onError: $message")
            }
        })
    }

    private fun getListImageFromUnsplash() {
        LoadImageFromUnsplash(this, unsplashKey).getListImageNormal(1, object : IUnsplashListImage {
            override fun onSuccess(images: MutableList<UnsplashObject>) {
                Log.wtf("LoadImageFromUnsplash", "images: ${images.size}")
                for (item in images) {
                    mListImage.add(Image(item.id, item.urls.regular))
                }
                dummyData()
            }

            override fun onError(message: String) {
                Log.wtf("LoadImageFromUnsplash", "onError: $message")
            }
        })
    }


    private fun searchImageUnsplash(query: String) {
        LoadImageFromUnsplash(
            this,
            unsplashKey
        ).searchListImage(1, query, object : IUnsplashListImage {
            override fun onSuccess(images: MutableList<UnsplashObject>) {
                Log.wtf("LoadImageFromUnsplash", "images: $images")
                for (item in images) {
                    mListImage.add(Image(item.id, item.urls.regular))
                }
                dummyData()
            }

            override fun onError(message: String) {
                Log.wtf("LoadImageFromUnsplash", "onError: $message")
            }
        })
    }


}