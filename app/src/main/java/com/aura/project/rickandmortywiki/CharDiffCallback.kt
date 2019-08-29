package com.aura.project.rickandmortywiki

import androidx.recyclerview.widget.DiffUtil
import com.aura.project.rickandmortywiki.main_characters.CharToShowItem
import com.aura.project.rickandmortywiki.main_characters.ErrorItem
import com.aura.project.rickandmortywiki.main_characters.ListItem
import com.aura.project.rickandmortywiki.main_characters.LoadingItem

class CharDiffCallback(private val oldList: List<ListItem>, private val newList: List<ListItem>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList[oldItemPosition]::class == newList[newItemPosition]::class) {
            when (oldList[oldItemPosition]) {
                is ErrorItem -> return true
                is LoadingItem -> return true
                is CharToShowItem -> {
                    val oldItem = oldList[oldItemPosition] as CharToShowItem
                    val newItem = newList[newItemPosition] as CharToShowItem
                    return oldItem.id == newItem.id
                }
            }
        }
        return false
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList[oldItemPosition] !is CharToShowItem)
            return oldList[oldItemPosition]::class == newList[newItemPosition]::class
        val oldItem = oldList[oldItemPosition] as CharToShowItem
        val newItem = newList[newItemPosition] as CharToShowItem
        return oldItem == newItem
    }


    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}