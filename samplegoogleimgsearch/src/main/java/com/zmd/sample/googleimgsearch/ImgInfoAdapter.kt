package com.zmd.sample.googleimgsearch

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zmd.lab.search.img.model.ImgInfo

class ImgInfoAdapter(private val activity: Activity, private val onClick: (ImgInfo) -> Unit) :
    ListAdapter<ImgInfo, ImgInfoAdapter.ImgInfoViewHolder>(ImgInfoDiffCallback) {

    class ImgInfoViewHolder(itemView: View, activity: Activity, val onClick: (ImgInfo) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.title)
        private val backTextView: TextView = itemView.findViewById(R.id.title_back)
        private val imgView: ImageView = itemView.findViewById(R.id.img)
        private var currentInfo: ImgInfo? = null
        private val activity = activity

        init {
            itemView.setOnClickListener {
                currentInfo?.let {
                    onClick(it)
                }
            }
        }

        fun bind(info: ImgInfo) {
            currentInfo = info
            textView.text = info.title
            backTextView.text = info.title
            if (info.origin.isNotEmpty()) {
                //Glide.with(activity).load(info.origin).centerCrop().into(imgView)
                Glide.with(activity).load(info.origin).into(imgView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ImgInfoViewHolder(view, activity, onClick)
    }

    override fun onBindViewHolder(holder: ImgInfoViewHolder, position: Int) {
        val info = getItem(position)
        holder.bind(info)
    }
}

object ImgInfoDiffCallback: DiffUtil.ItemCallback<ImgInfo>() {
    override fun areItemsTheSame(oldItem: ImgInfo, newItem: ImgInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImgInfo, newItem: ImgInfo): Boolean {
        return oldItem.id == newItem.id
    }
}