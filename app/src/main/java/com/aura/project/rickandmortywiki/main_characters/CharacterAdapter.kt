package com.aura.project.rickandmortywiki.main_characters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.*

class CharacterAdapter(fragment: Fragment) : ListDelegationAdapter<List<ListItem>>() {

    private val _PAGE_SIZE = 20
    private var onCharClickListener: OnCharClickListener? = fragment as OnCharClickListener
    private var loader: CharacterLoader? = fragment as CharacterLoader
    private val scope = CoroutineScope(Dispatchers.Main)
    private var diffJob = Job()

    init {
        delegatesManager.apply {
            addDelegate(characterDelegate { onCharClickListener?.onCharClicked(it.id) })
            addDelegate(errorDelegate { onCharClickListener?.onErrorClick() })
        }
    }

    var itemList: List<ListItem> = ArrayList()
        set(value) {
            diffJob.cancel()
            diffJob = scope.launch {
                val diffResult = calculateDiff(field, value)
                field = value
                setItems(value)
                diffResult.dispatchUpdatesTo(this@CharacterAdapter)
            }
        }

    private suspend fun calculateDiff(
        oldList: List<ListItem>,
        newList: List<ListItem>
    ): DiffUtil.DiffResult =
        withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(CharDiffCallback(oldList, newList))
        }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder)
        if (itemList.size - position == _PAGE_SIZE) loader?.endReached()
    }

    @ExperimentalCoroutinesApi
    fun onDestroy() {
        onCharClickListener = null
        diffJob.cancel()
    }

    interface OnCharClickListener {
        fun onCharClicked(characterId: Long)
        fun onErrorClick()
    }

    interface CharacterLoader {
        fun endReached()
    }
}