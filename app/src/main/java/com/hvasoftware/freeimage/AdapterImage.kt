package com.hvasoftware.freeimage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hvasoftware.freeimage.databinding.ItemImageBinding


class AdapterImage(
    private val mContext: Context,
    private val itemClickedListener: (Int) -> Unit
) : RecyclerView.Adapter<AdapterImage.MyViewHolder>() {

    private var mData: MutableList<Image> = arrayListOf()

    fun setData(data: MutableList<Image>) {
        this.mData = data
        notifyItemRangeChanged(0, itemCount)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = mData[position]
        holder.rootView.setOnClickListener {
            itemClickedListener(position)
        }
        Glide.with(mContext)
            .load(image.url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .thumbnail(Glide.with(mContext).load(com.hvasoftware.image.R.drawable.img_loading))
            .apply(RequestOptions())
            .into(holder.imgView)
    }


    class MyViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val rootView: ConstraintLayout = binding.rootView
        val imgView: ImageView = binding.imgView
    }

    override fun getItemCount(): Int {
        return mData.size
    }


}
