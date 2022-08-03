package com.apero.videoeditor.extension

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.inflateLayout(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}

inline fun Context.alert(func: AlertDialog.Builder.() -> AlertDialog.Builder) {
    AlertDialog.Builder(this).func().show()
}

fun Context.toastShort(text: CharSequence?) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Fragment.toastShort(text: CharSequence?) = Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()

fun Context.toastShort(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
fun Fragment.toastShort(resId: Int) = Toast.makeText(this.context, resId, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: CharSequence?) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

fun Context.isInternetAvailable(): Boolean = run {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected)
}
fun Fragment.isInternetAvailable(): Boolean = run {
    val connectivityManager = this.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected)
}
