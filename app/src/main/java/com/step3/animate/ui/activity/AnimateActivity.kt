package com.step3.animate.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.room.Transaction
import com.google.android.material.textfield.TextInputEditText
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity
import com.step3.animate.modules.base.SeekBarChangeListener
import com.step3.animate.modules.room.entity.Animate
import com.step3.animate.modules.store.AnimateStore
import com.step3.animate.utils.Files

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */
class AnimateActivity : AppActivity() {

    private var fps =  12
    private var pid =  0
    private var pPath = ""
    private var animItem: Animate? = null
    private lateinit var animStore: AnimateStore
    private lateinit var nameView: TextInputEditText
    private lateinit var descView: TextInputEditText
    private lateinit var memoView: TextInputEditText
    private lateinit var coverView: AppCompatImageView
    private lateinit var countView: AppCompatTextView
    private lateinit var fpsView: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_animate)
        animStore = AnimateStore(applicationContext)
        initView()
        pid = intent.getIntExtra("pid", 0)
        setAnimInfo()
    }

    override fun onResume() {
        super.onResume()
        setAnimInfo()
    }

    @Transaction
    private fun initView() {
         nameView = findViewById<TextInputEditText>(R.id.anim_detail_name)
         descView = findViewById<TextInputEditText>(R.id.anim_detail_desc)
         memoView = findViewById<TextInputEditText>(R.id.anim_detail_memo)
         coverView = findViewById<AppCompatImageView>(R.id.anim_detail_cover)
         countView = findViewById<AppCompatTextView>(R.id.anim_detail_count)
         fpsView = findViewById<SeekBar>(R.id.anim_detail_fps)

        fpsView.setOnSeekBarChangeListener(object :
                SeekBarChangeListener() {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    fps = p1
                }
            })

        findViewById<Button>(R.id.commit_anim_btn).setOnClickListener(View.OnClickListener {
            val name = nameView.text!!.toString().trim()
            if (name.isEmpty()) {
                nameView.error = "项目名不能为空"
            }
            val desc = descView.text!!.toString().trim()
            val fps = fpsView.progress
            val memo = memoView.text!!.toString().trim()
            saveProject(name, desc, fps, memo)
        })

        coverView.setOnClickListener(View.OnClickListener {
            openGallery()
        })
    }

    private fun setAnimInfo() {
        if (pid > 0) {
            animItem = animStore.findById(pid)

            pPath = animItem!!.path
            fpsView.progress = fps
            descView.setText(animItem!!.desc)
            memoView.setText(animItem!!.memo)
            countView.text = "${animItem!!.count}张"
            coverView.setImageURI(Uri.parse(animItem!!.cover))
        }
    }

    @Transaction
    private fun saveProject(name: String, desc: String, fps: Int, memo: String) {
        pPath = pPath.ifEmpty { Files.getProjectPath() }
        if(pid > 0) {
            animStore.update(animItem!!)
        }else {
            pid = animStore.insert(Animate(name, pPath, fps, desc, memo)).toInt()
        }
        openCamera()
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra("pid", pid)
        intent.putExtra("folder", pPath)
        startActivity(intent)
    }

    private fun openGallery() {
        if(pid > 0) {
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("pid", pid)
            startActivity(intent)
        }
    }
}