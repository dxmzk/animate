package com.step3.animate.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.step3.animate.R
import com.step3.animate.modules.room.entity.Photo

class GalleryAdapter(private val mContext: Context, private val list: ArrayList<Photo>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.ada_gallery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.imgView.setImageURI(Uri.parse(item.path))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
         val imgView: AppCompatImageView

        init {
            imgView = view.findViewById(R.id.gallery_item_img)
        }
    }

}
