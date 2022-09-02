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
            addAnimate()
        })
        recycleView = findViewById(R.id.anim_list_recycler)
        val lm  = LinearLayoutManager(this)
        recycleView.layoutManager = lm
//        openCamera()
    }

    private fun updateList(list: ArrayList<Animate>) {
        itemList.clear()
        itemList.addAll(list)
        if(recycleAdapter == null) {
            recycleAdapter = AnimListAdapter(this, itemList)
            recycleView.adapter = recycleAdapter
        }else {
            recycleAdapter?.notifyDataSetChanged()
        }
        Log.i(TAG, itemList.toString())
    }

    private fun addAnimate() {
        val intent = Intent(this, AnimateActivity::class.java)
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
//        list?.forEach {
//            Log.i(TAG, it.toString())
//        }
        if (list.isNotEmpty()) {
            updateList(list as ArrayList<Animate>)
        }
    }
}