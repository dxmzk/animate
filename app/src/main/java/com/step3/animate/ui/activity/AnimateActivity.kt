package com.step3.animate.ui.activity

import android.content.Intent
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

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */
class AnimateActivity : AppActivity() {

    private var fps =  12
    private var pid =  0
    private var pPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_animate)

        initView()
    }

    private fun initView() {
        val nameView = findViewById<TextInputEditText>(R.id.anim_detail_name)
        val descView = findViewById<TextInputEditText>(R.id.anim_detail_desc)
        val memoView = findViewById<TextInputEditText>(R.id.anim_detail_memo)
        val coverView = findViewById<AppCompatImageView>(R.id.anim_detail_cover)
        val countView = findViewById<AppCompatTextView>(R.id.anim_detail_count)
        val fpsView = findViewById<SeekBar>(R.id.anim_detail_fps)
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
            val memo = memoView.text!!.toString().trim()
            saveProject(name, desc, memo)
        })

    }

    @Transaction
    private fun saveProject(name: String, desc: String, memo: String) {
        val animStore = AnimateStore(applicationContext)
        pPath = pPath.ifEmpty { "po${System.currentTimeMillis()}" }
        animStore.insert(Animate(name, pPath, fps, desc, memo))
        openCamera();
    }

    private fun openCamera() {

        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra("pid", pid)
        intent.putExtra("folder", pPath)
        startActivity(intent)
    }
}