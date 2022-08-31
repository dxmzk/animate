package com.step3.animate.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */
class AnimateActivity: AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_animate)

        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.create_animate_btn).setOnClickListener(View.OnClickListener {
            openCamera()
        })
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}