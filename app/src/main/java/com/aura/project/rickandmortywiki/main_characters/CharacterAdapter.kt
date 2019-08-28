package com.aura.project.rickandmortywiki.main_characters

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.*

class CharacterAdapter(fragment: Fragment) : ListDelegationAdapter<List<ListItem>>() {

    private var onItemClickListener: OnItemClickListener? = fragment as OnItemClickListener
    private var loader: CharacterLoader? = fragment as CharacterLoader
    private val scope = CoroutineScope(Dispatchers.Main)
    private var diffJob = Job()

    init {
        items = listOf()
        delegatesManager.apply {
            addDelegate(characterDelegate { onItemClickListener?.onCharClicked(it.id) })
            addDelegate(errorDelegate { onItemClickListener?.onErrorClick() })
        }
    }

    var itemList: List<ListItem>
        set(value) {
            diffJob.cancel()
            diffJob = scope.launch {
                val diffResult = calculateDiff(items, value)
                diffResult.dispatchUpdatesTo(this@CharacterAdapter)
                setItems(value)
            }
        }
        get() = items

    private suspend fun calculateDiff(
        oldList: List<ListItem>,
        newList: List<ListItem>
    ): DiffUtil.DiffResult =
        withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(CharDiffCallback(oldList, newList))
        }

    @ExperimentalCoroutinesApi
    fun onDestroy() {
        onItemClickListener = null
        loader = null
        scope.cancel()
    }

    interface OnItemClickListener {
        fun onCharClicked(characterId: Long)
        fun onErrorClick()
    }

    interface CharacterLoader {
        fun endReached()
    }
}