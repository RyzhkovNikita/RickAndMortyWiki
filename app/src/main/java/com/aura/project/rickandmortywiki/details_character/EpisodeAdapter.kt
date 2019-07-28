package com.aura.project.rickandmortywiki.details_character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.R

class EpisodeAdapter(fragment: OnEpisodeClickListener) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private var _onEpisodeClickListener: OnEpisodeClickListener? = fragment

    var list: List<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) = holder bindBy list[position]

    interface OnEpisodeClickListener {
        fun onEpisodeClick(episode: String)
    }

    fun onDestroy() {
        _onEpisodeClickListener = null
    }

    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            _onEpisodeClickListener?.onEpisodeClick(list[adapterPosition])
        }

        private var name: TextView = itemView.findViewById(R.id.episode_item)


        infix fun bindBy(episode: String) {
            name.text = episode
        }
    }
}