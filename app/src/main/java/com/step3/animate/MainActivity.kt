package com.step3.animate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Transaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.step3.animate.modules.store.AnimateStore
import com.step3.animate.ui.activity.AnimateActivity
import com.step3.animate.ui.activity.CameraActivity
/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var animStore: AnimateStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAnimates()
        initView()
    }

    private fun initView() {
        findViewById<FloatingActionButton>(R.id.add_animate_btn).setOnClickListener(View.OnClickListener {
            addAnimate()
        })
//        openCamera()
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
        list?.forEach {
            Log.i(TAG, it.toString())
        }
    }
}