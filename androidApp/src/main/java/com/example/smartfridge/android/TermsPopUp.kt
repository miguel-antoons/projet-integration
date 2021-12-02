package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window

class TermsPopUp (
    private val adapter: Login,
        ): Dialog(adapter) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_pop_up_terms)
    }
}