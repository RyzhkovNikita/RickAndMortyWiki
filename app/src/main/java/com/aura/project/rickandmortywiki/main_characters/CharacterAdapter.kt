package com.aura.project.rickandmortywiki.main_characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.aura.project.rickandmortywiki.ImageLoader
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character
import kotlinx.coroutines.*

class CharacterAdapter(fragment: AllCharFragment) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val _PAGE_SIZE = 20
    private var _onCharClickListener: OnCharClickListener? = fragment
    private var _loader: CharacterLoader? = fragment
    private val _context = fragment.context
    private val _scope = CoroutineScope(Dispatchers.Main)

    var charList: List<Character> = ArrayList()
        set(value) {
            _scope.launch {
                val diffResult = calculateDiff(field, value)
                field = value
                diffResult.dispatchUpdatesTo(this@CharacterAdapter)
            }
        }

    private suspend fun calculateDiff(oldList: List<Character>, newList: List<Character>): DiffUtil.DiffResult =
        withContext(Dispatchers.IO) {
            DiffUtil.calculateDiff(CharDiffCallback(oldList, newList))
        }

    override fun getItemCount(): Int = charList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(LayoutInflater.from(_context).inflate(R.layout.character_card, parent, false))

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder bindBy charList[position]
        if (charList.size - position == _PAGE_SIZE) _loader?.endReached()
    }


    @ExperimentalCoroutinesApi
    fun onDestroy() {
        _onCharClickListener = null
        _scope.cancel()
    }

    interface OnCharClickListener {
        fun onCharClicked(character: Character)
    }

    interface CharacterLoader {
        fun endReached()
    }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            _onCharClickListener?.onCharClicked(charList[adapterPosition])
        }

        private var avatar: ImageView = itemView.findViewById(R.id.char_avatar)
        private var name: TextView = itemView.findViewById(R.id.char_name_tv)


        infix fun bindBy(character: Character) {
            name.text = character.name
            ImageLoader
                .with(_context!!)
                .from(character.image)
                .to(avatar)
                .cutCircle()
                .errorImage(R.drawable.char_error_avatar)
                .load()
        }
    }
}