package com.apero.videoeditor.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat

fun View.showIf(show: Boolean) {
    if (show) {
        show()
    } else {
        hide()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}


fun View.hide() {
    this.visibility = View.GONE
}

inline fun WebView.setProgressChangedListener(crossinline onProgressChanged: (Int) -> Unit) {
    this.webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            onProgressChanged.invoke(newProgress)
        }
    }
}

fun View.circularReveal(backgroundColor: Int) {
    val showAndSetBackgroundColorFunction = {
        this.setBackgroundColor(backgroundColor)
        this.visibility = View.VISIBLE
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.post {
            val cx = this.width / 2
            val cy = this.height / 2
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            try {
                val animator = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
                animator.startDelay = 50
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        showAndSetBackgroundColorFunction.invoke()
                    }
                })
                animator.start()
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    } else {
        showAndSetBackgroundColorFunction.invoke()
    }
}

fun TextView.formatMoney(count:String){
    val number: Double = count.toDouble()
     if (number < 0 && number > -1000 || number > 0 && number < 1000) {
         this.text = count
         return
    }
    try {
        val formatter: NumberFormat = DecimalFormat("###,###")
        var resp = formatter.format(number)
        resp = resp.replace(",".toRegex(), ".")
        this.text =   resp
    } catch (e: java.lang.Exception) {
        this.text = count
    }
}
