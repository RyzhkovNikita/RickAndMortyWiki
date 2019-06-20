package com.aura.project.rickandmortywiki.main_characters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class CharacterAdapter(private var fragment: AllCharFragment?) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)

    var charList: List<Character> = ArrayList()
        set(value) {
            scope.launch {
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
        CharacterViewHolder(fragment?.context!!, parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(charList[position])


    @ExperimentalCoroutinesApi
    fun onDestroy() {
        fragment = null
        scope.cancel()
    }


    class CharacterViewHolder(private val context: Context, parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.character_card, parent, false)),
        View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("call fragment from where")
        }

        private var avatar: ImageView = itemView.findViewById(R.id.char_avatar)
        private var name: TextView = itemView.findViewById(R.id.char_name_tv)


        fun bind(character: Character) {
            name.text = character.name
            Glide.with(context).load(character.image).into(avatar)
        }
    }
}