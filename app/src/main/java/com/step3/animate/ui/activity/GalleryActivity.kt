package com.step3.animate.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity
import com.step3.animate.modules.room.entity.Photo
import com.step3.animate.modules.store.AnimateStore
import com.step3.animate.modules.store.PhotoStore
import com.step3.animate.ui.adapter.GalleryAdapter

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 */
class GalleryActivity : AppActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private var photoList: ArrayList<Photo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_gallery)

        initView()
        setPhoto()
    }

    private fun initView() {
        recyclerView = findViewById<RecyclerView>(R.id.gallery_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        galleryAdapter = GalleryAdapter(this, photoList)
        recyclerView.adapter = galleryAdapter
    }

//    @SuppressLint("NotifyDataSetChanged")
    private fun setPhoto() {
        val pid = intent.getIntExtra("pid", 0)
        if (pid > 0) {
            val photoStore = PhotoStore(applicationContext)
            val list = photoStore.findByAnimId(pid)
            if (list != null && list.isNotEmpty()) {
                photoList.addAll(list)
                galleryAdapter.notifyDataSetChanged()
            }
        }
    }
}