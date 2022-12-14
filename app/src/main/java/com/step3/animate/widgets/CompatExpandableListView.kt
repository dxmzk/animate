package com.step3.animate.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ExpandableListView

/**
 * Author: Meng
 * Date: 2022/12/07
 * Desc:
 */
class CompatExpandableListView(context: Context, attr: AttributeSet) :
    ExpandableListView(context, attr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasure = MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE / 2, MeasureSpec.AT_MOST
        );
        super.onMeasure(widthMeasureSpec, heightMeasure)
        val params = layoutParams
        params.height = measuredHeight
    }
}