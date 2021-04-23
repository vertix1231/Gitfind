package com.dicoding.bangkit.android.fundamental.gitfind.onclick

import android.view.View

//membuat item bisa diklik di dalam adapter
class CustomOnItemClickListener(private val position: Int, private val onItemClickCallback: OnItemClickCallback) : View.OnClickListener {
    override fun onClick(view: View) {
        onItemClickCallback.onItemClicked(view, position)
    }
    interface OnItemClickCallback {
        fun onItemClicked(view: View, position: Int)
    }
}