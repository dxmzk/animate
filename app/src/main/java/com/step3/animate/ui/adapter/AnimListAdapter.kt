package com.step3.animate.ui.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.step3.animate.R
import com.step3.animate.modules.room.entity.Animate

/**
 * Author: Meng
 * Date: 2022/09/02
 * Desc:
 */
class AnimListAdapter(private val context: Context, private val animList: ArrayList<Animate>) :
    RecyclerView.Adapter<AnimListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ada_anim_item, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimListAdapter.ViewHolder, position: Int) {
        val item = animList.get(position)
        holder.coverView.setImageURI(Uri.parse(item.path))
        holder.nameView.text = "${item.name}"
        holder.countView.text = "${item.count}张"
        holder.descView.text = "${item.desc}"

        Log.i("TAG", item.toString())
    }

    override fun getItemCount(): Int {
        return animList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var coverView: AppCompatImageView
        var nameView: AppCompatTextView
        var descView: AppCompatTextView
        var countView: AppCompatTextView

        init {
            coverView = view.findViewById(R.id.anim_item_cover)
            nameView = view.findViewById(R.id.anim_item_name)
            countView = view.findViewById(R.id.anim_item_count)
            descView = view.findViewById(R.id.anim_item_desc)
        }
    }

}