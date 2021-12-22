package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageView

class LegendPopUp (
    context: MainActivity,
): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.legend_popup)

        setupCloseButton()
    }
    // close the pop-up when clicking on the close button
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.leg_close_popup).setOnClickListener {
            dismiss()
        }
    }
}