package com.example.tonightsmovie.utils.ext

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.tonightsmovie.R
import jp.wasabeef.glide.transformations.BlurTransformation

fun ImageView.load(url: String) {
    val drawable = CircularProgressDrawable(context).apply {
        setColorSchemeColors(ContextCompat.getColor(context, R.color.gray300))
        centerRadius = 30f
        strokeWidth = 5f
        start()
    }

    Glide
        .with(context)
        .load(url)
        .placeholder(drawable)
        .into(this)
}

fun ImageView.loadImageWithBlur(url: String) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {
                // Set the original image
                this@loadImageWithBlur.setImageBitmap(resource)

                // Apply a blur effect to image background
                val bitmap: Drawable = BitmapDrawable(resources, resource)
                Glide.with(context)
                    .load(bitmap)
                    .apply(
                        RequestOptions.bitmapTransform(
                            BlurTransformation(50, 5)
                        )
                    )
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            this@loadImageWithBlur.background = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                /* Do nothing */
            }
        })
}