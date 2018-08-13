package com.reigndesign.test.adapters

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import com.reigndesign.test.R

open class MySimpleCallback(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs
        , swipeDirs) {
    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder
                        , p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView
                             , viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float
                             , actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView

        val background = ColorDrawable()
        background.color = ContextCompat.getColor(itemView.context, R.color.colorAccent)
        background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
        )
        background.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState
                , isCurrentlyActive)
    }
}