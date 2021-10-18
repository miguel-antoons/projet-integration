package com.example.smartfridge.android.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// This class gives some decoration / design to the recyclerview
class FridgesItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = 80 }
}