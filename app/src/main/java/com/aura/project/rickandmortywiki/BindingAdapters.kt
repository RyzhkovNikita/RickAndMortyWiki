package com.aura.project.rickandmortywiki

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageURL")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        ImageLoader
            .with(imageView.context!!)
            .from(url)
            .to(imageView)
            .errorImage(R.drawable.char_error_avatar)
            .load()
    }
}

@BindingAdapter("TFvisibility")
fun bindVisibility(view: View, isVisible: Boolean?) {
    isVisible?.let {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("gender")
fun bindGender(textView: TextView, gender: String?) {
    gender?.let {
        val genderSignDrawable: Drawable? = when (gender) {
            "Male" -> textView.context?.getDrawable(R.drawable.male_sign)
            "Female" -> textView.context?.getDrawable(R.drawable.female_sign)
            else -> null
        }
        val pixelDrawableSize = (textView.lineHeight * 0.8).toInt()
        genderSignDrawable?.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize)
        textView.setCompoundDrawables(null, null, genderSignDrawable, null)
        textView.compoundDrawablePadding = 8
    }
}