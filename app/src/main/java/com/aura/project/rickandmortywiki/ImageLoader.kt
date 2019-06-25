package com.aura.project.rickandmortywiki

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageLoader {
    /**
     * if it will be need, it may be replaced by Picasso or some else implementation
     */
    companion object {
        fun with(context: Context): ImageLoaderSettings = ImageLoaderSettings(context)
    }

    class ImageLoaderSettings(val context: Context) {
        private var url = ""
        private var placeholder: ImageView? = null
        private var cutCircle = false
        private var timeoutInMs = 10000
        private var errorImageId = -1
        private var errorDrawable: Drawable? = null
        fun from(url: String): ImageLoaderSettings {
            this.url = url
            return this
        }

        fun to(placeholder: ImageView): ImageLoaderSettings {
            this.placeholder = placeholder
            return this
        }

        fun cutCircle(): ImageLoaderSettings {
            this.cutCircle = true
            return this
        }

        fun timeoutInMs(milliseconds: Int): ImageLoaderSettings {
            this.timeoutInMs = milliseconds
            return this
        }

        fun errorImage(resourseId: Int): ImageLoaderSettings {
            this.errorImageId = resourseId
            return this
        }

        fun errorImage(drawable: Drawable): ImageLoaderSettings {
            this.errorDrawable = drawable
            return this
        }

        fun load() {
            val manager =
                Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .timeout(timeoutInMs)
            if (cutCircle)
                manager.circleCrop()
            if (errorImageId != -1)
                manager.error(errorImageId)
            else if (errorDrawable != null)
                manager.error(errorDrawable)
            manager
                .into(placeholder!!)
        }

    }
}