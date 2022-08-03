package com.apero.videoeditor.utils

import android.widget.FrameLayout
import android.widget.TextView
import android.os.Bundle
import com.apero.videoeditor.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window

class DialogExitAppNoAds(context: Context) : Dialog(
    context
) {
    private var frameLayout: FrameLayout? = null
    private var btnExit: TextView? = null
    private var btnCancel: TextView? = null
    private var view: View? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_exit_app)
        initView()
    }

    private fun initView() {
        btnExit = findViewById(R.id.btnExit)
        btnCancel = findViewById(R.id.btnCancel)
        frameLayout = findViewById(R.id.frAds)
        view = findViewById(R.id.view)
        frameLayout?.visibility = View.GONE
        view?.visibility = View.INVISIBLE
        btnExit?.setOnClickListener { (context as Activity).finish() }
        btnCancel?.setOnClickListener { dismiss() }
    }
}