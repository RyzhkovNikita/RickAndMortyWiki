package com.aura.project.rickandmortywiki

import androidx.recyclerview.widget.DiffUtil
import com.aura.project.rickandmortywiki.data.Character

class CharDiffCallback(private val oldList: List<Character>, private val newList: List<Character>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].image == newList[newItemPosition].image
                && oldList[oldItemPosition].status == newList[newItemPosition].status

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}