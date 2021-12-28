package com.example.testyogiyo.util

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class Decoration(
    private val height: Float,
    private val padding: Float,
    @ColorInt private val color: Int,
) : RecyclerView.ItemDecoration() {
    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            parent.paddingStart + padding
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN_MR1")
        }
        val right = parent.width - parent.paddingEnd - padding

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top+height
            c.drawRect(left, top, right, bottom, paint)
        }
    }
}