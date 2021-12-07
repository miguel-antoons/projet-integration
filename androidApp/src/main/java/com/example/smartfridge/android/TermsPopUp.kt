package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window

class TermsPopUp (
    context: Login,
        ): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.terms_popup)
    }
}