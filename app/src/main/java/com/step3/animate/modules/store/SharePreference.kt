package com.step3.animate.modules.store

import android.content.Context
import android.content.SharedPreferences
import com.step3.animate.R

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */
class SharePreference {

    companion object {
        private lateinit var sharedPref: SharedPreferences
        fun getIns(context: Context): SharedPreferences {
//            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) // 如只需使用 Activity 的一个共享首选项，请从 Activity 中使用此方法
            sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE
            ) // 如果需要多个由名称标识的共享偏好设置文件，则使用此方法。可以从您的应用中的任何 Context 调用此方法
            return sharedPref
        }

        fun setValue(context: Context, key: String, value: String) {
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
                // apply() 异步写入数据, 会立即更改内存中的对象
                // commit() 同步写入数据，应避免从主线程调用它
            }
        }
    }
}