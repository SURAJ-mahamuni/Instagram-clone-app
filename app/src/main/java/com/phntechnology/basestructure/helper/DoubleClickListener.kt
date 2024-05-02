package com.phntechnology.basestructure.helper

import android.view.View

abstract class DoubleClickListener : View.OnClickListener {
    var lastClick: Long = 0

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA = 300
    }

    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClick < DOUBLE_CLICK_TIME_DELTA){
            onDoubleClick(v)
        }
        lastClick = clickTime
    }

    abstract fun onDoubleClick(v: View?)
}