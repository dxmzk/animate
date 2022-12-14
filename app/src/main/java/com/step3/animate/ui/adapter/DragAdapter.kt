package com.step3.animate.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DragAdapter(private val mContext: Context, private val mList: ArrayList<String>) :
    RecyclerView.Adapter<DragAdapter.ViewHolder>() {

    val fixedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

}
