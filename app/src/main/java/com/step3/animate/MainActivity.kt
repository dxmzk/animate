package com.step3.animate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Transaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.step3.animate.modules.room.entity.Animate
import com.step3.animate.modules.store.AnimateStore
import com.step3.animate.ui.activity.AnimateActivity
import com.step3.animate.ui.activity.CameraActivity
import com.step3.animate.ui.activity.WebActivity
import com.step3.animate.ui.adapter.AnimListAdapter

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var animStore: AnimateStore
    private var itemList = ArrayList<Animate>()
    private lateinit var recycleView: RecyclerView
    private var recycleAdapter: AnimListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    override fun onResume() {
        super.onResume()
        getAnimates()
    }

    private fun initView() {
        findViewById<FloatingActionButton>(R.id.add_animate_btn).setOnClickListener(View.OnClickListener {
            addAnimate(null)
        })
        recycleView = findViewById(R.id.anim_list_recycler)
        val lm  = LinearLayoutManager(this)
        recycleView.layoutManager = lm

    }

    private fun updateList(list: ArrayList<Animate>) {
        itemList.clear()
        itemList.addAll(list)
        if(recycleAdapter == null) {
            recycleAdapter = AnimListAdapter(this, itemList)
            recycleAdapter?.addOnItemClickListener(object :AnimListAdapter.OnItemClickListener<Animate> {
                override fun onItemClick(data: Animate, position: Int) {
                    addAnimate(data)
                }
            })
            recycleView.adapter = recycleAdapter
        }else {
            recycleAdapter?.notifyDataSetChanged()
        }
    }

    private fun addAnimate(data: Animate?) {
        val intent = Intent(this, WebActivity::class.java)
        if (data != null) {
            intent.putExtra("pid", data.id)
            intent.putExtra("folder", data.path)
            intent.putExtra("title", data.name)
            intent.putExtra("desc", data.desc)
            intent.putExtra("memo", data.memo)
            intent.putExtra("fps", data.fps)
            intent.putExtra("cover", data.cover)
        }
        startActivity(intent)
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    @Transaction
    private fun getAnimates() {
        animStore = AnimateStore(applicationContext)
        val list = animStore.getAll()
        if (list.isNotEmpty()) {
            updateList(list as ArrayList<Animate>)
        }
    }
}