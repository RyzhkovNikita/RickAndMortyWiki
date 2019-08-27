package com.aura.project.rickandmortywiki.main_characters

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.aura.project.rickandmortywiki.ImageLoader
import com.aura.project.rickandmortywiki.R
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate

fun characterDelegate(onClick: (CharToShowItem) -> Unit) =
    adapterDelegate<CharToShowItem, ListItem>(R.layout.character_card) {
        val name: TextView = findViewById(R.id.char_name_tv)
        val avatar: ImageView = findViewById(R.id.char_avatar)
        itemView.setOnClickListener { onClick(item) }
        bind {
            name.text = item.name
            ImageLoader
                .with(itemView.context)
                .from(item.imageUrl)
                .to(avatar)
                .cutCircle()
                .errorImage(R.drawable.char_error_avatar)
                .load()
        }
    }

fun errorDelegate(onTryClick: () -> Unit) =
    adapterDelegate<ErrorItem, ListItem>(R.layout.error_layout) {
        val tryButton: Button = findViewById(R.id.error_button)
        tryButton.setOnClickListener { onTryClick() }
    }