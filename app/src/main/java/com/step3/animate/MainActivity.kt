package com.step3.animate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.step3.animate.ui.activity.CameraActivity
/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}