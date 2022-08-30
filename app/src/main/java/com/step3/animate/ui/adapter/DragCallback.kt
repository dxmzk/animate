package com.step3.animate.ui.adapter;

import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.step3.animate.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Author: Meng
 * Date: 2022/08/29
 * Desc:
 * 使用：
 *  val dragCallBack = DragCallBack(mAdapter, list)
    val itemTouchHelper = ItemTouchHelper(dragCallBack)
    itemTouchHelper.attachToRecyclerView(mBinding.recycleView)

 */
class DragCallback(adapter: DragAdapter, data: MutableList<String>) : ItemTouchHelper.Callback() {
    private val TAG = "DragCallback";
    private lateinit var mAdapter: DragAdapter;
    private lateinit var mList: ArrayList<String>;

    /**
     * 交互方式，交互方式分为两种：
        拖拽，网格布局支持上下左右，列表只支持上下（LEFT、UP、RIGHT、DOWN）
        滑动，只支持前后（START、END）
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlags = 0
        var swipeFlags = 0
        when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                // 网格布局
                dragFlags =
                    ItemTouchHelper.LEFT or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, swipeFlags)
            }
            is LinearLayoutManager -> {
                // 线性布局
                dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }
            else -> {
                // 其他情况可自行处理
                return 0
            }
        }
    }

    /**
     * 拖拽时回调，主要对起始位置和目标位置的item做数据交换，然后刷新视图
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 起始位置
        val fromPosition = viewHolder.adapterPosition
        // 结束位置
        val toPosition = target.adapterPosition
        // 固定位置
        if (fromPosition == mAdapter.fixedPosition || toPosition == mAdapter.fixedPosition) {
            return false
        }
        // 根据滑动方向 交换数据
        if (fromPosition < toPosition) {
            // 含头不含尾
            for (index in fromPosition until toPosition) {
                Collections.swap(mList, index, index + 1)
            }
        } else {
            // 含头不含尾
            for (index in fromPosition downTo toPosition + 1) {
                Collections.swap(mList, index, index - 1)
            }
        }
        // 刷新布局
        mAdapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    /**
     * 滑动时回调，主要做数据和视图的更新
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.START) {
            Log.i(TAG, "START--->向左滑")
        } else {
            Log.i(TAG, "END--->向右滑")
        }
        val position = viewHolder.adapterPosition
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }

    /**
     * 拖拽或滑动时回调，可以修改item的视图
     * actionState：
        ACTION_STATE_IDLE   空闲状态
        ACTION_STATE_SWIPE  滑动状态
        ACTION_STATE_DRAG   拖拽状态
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.let {
                // 因为拿不到recyclerView，无法通过recyclerView.layoutManager来判断是什么布局，所以用item的宽度来判断
                // itemView.width > 500 用这个来判断是否是线性布局，实际取值自己看情况
                if (it.itemView.width > 500) {
                    // 线性布局 设置背景颜色
                    val drawable = it.itemView.background as GradientDrawable
                    drawable.color =
                        ContextCompat.getColorStateList(it.itemView.context, R.color.purple_500)
                } else {
                    // 网格布局 设置选中放大
                    ViewCompat.animate(it.itemView).setDuration(200).scaleX(1.3F).scaleY(1.3F)
                        .start()
                }
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    /**
     * 拖拽或滑动结束 时回调，把改变后的item视图恢复到初始状态
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        // 这里不能用if判断，因为GridLayoutManager是LinearLayoutManager的子类，改用when，类型推导有区别
        when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                // 网格布局 设置选中大小
                ViewCompat.animate(viewHolder.itemView).setDuration(200).scaleX(1F).scaleY(1F)
                    .start()
            }
            is LinearLayoutManager -> {
                // 线性布局 设置背景颜色
                val drawable = viewHolder.itemView.background as GradientDrawable
                drawable.color =
                    ContextCompat.getColorStateList(viewHolder.itemView.context, R.color.purple_200)
            }
        }
        super.clearView(recyclerView, viewHolder)
    }

    /**
     * 以拖拽举例，需要重写isLongPressDragEnabled方法把它禁掉，然后再非固定位置的时候去手动开启。
     */
    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }
}
