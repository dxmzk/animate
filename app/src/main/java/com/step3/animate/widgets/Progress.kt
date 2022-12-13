package com.step3.animate.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.step3.animate.R

/**
 * Author: Meng
 * Date: 2022/12/13
 * Desc:
 */
class Progress(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

//        val view = LayoutInflater.from(context).inflate(R.layout., this, false)

        val a = context.obtainStyledAttributes(attrs, R.styleable.Progress)
        val text = a.getString(R.styleable.Progress_text) ?: ""
        val valueSize = a.getFloat(R.styleable.Progress_value, 0f)
        val valueColor = a.getInt(R.styleable.Progress_txColor, 0)
        val marginV = a.getDimension(R.styleable.Progress_txSize, 0f).toInt()
        a.recycle()
    }
}