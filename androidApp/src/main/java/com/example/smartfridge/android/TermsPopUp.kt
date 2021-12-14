package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window

class TermsPopUp (
    context: SignUp,
        ): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.terms_popup)
    }
}