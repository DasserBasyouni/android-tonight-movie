package com.example.tonightsmovie.utils

import android.icu.text.NumberFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tonightsmovie.utils.ext.loadImageWithBlur

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:loadImageWithBlur")
fun loadImageWithBlur(imageView: ImageView, url: String) {
    imageView.loadImageWithBlur(url)
}

@BindingAdapter("app:prefix", "app:money")
fun moneyAsText(textView: TextView, prefix: String, money: Long) {
    val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
    formatter.maximumFractionDigits = 0
    val formattedMoney = formatter.format(money)

    textView.text = String.format("%s %s", prefix, formattedMoney)
}


