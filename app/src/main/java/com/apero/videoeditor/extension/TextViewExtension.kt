package com.apero.videoeditor.extension

import android.annotation.SuppressLint
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat

fun TextView.setTextMoney(string: String){
    val number = string.toDouble()
      if (number < 0 && number > -1000 || number > 0 && number < 1000) {
          this.text =   number.toString()
    } else try {
        val formatter: NumberFormat = DecimalFormat("###,###")
        var resp = formatter.format(number)
        resp = resp.replace(",".toRegex(), ".")
        this.text = resp
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@SuppressLint("SetTextI18n")
fun TextView.setTextMoney(string: String, note:String){
    val number = string.toDouble()
      if (number < 0 && number > -1000 || number > 0 && number < 1000) {
          this.text = "$number $note"
    } else try {
        val formatter: NumberFormat = DecimalFormat("###,###")
        var resp = formatter.format(number)
        resp = resp.replace(",".toRegex(), ".")
        this.text = "$resp $note"
    } catch (e: Exception) {
        e.printStackTrace()
    }

}