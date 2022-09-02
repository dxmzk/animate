package com.step3.animate.ui.activity

import android.content.Intent
import android.os.Bundle
import com.step3.animate.MainActivity
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity

/**
 * Author: Meng
 * Date: 2022/09/02
 * Desc:
 */
class LaunchActivity: AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_launch)
    }

    override fun onResume() {
        super.onResume()
        openApp()
    }

    private fun openApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}