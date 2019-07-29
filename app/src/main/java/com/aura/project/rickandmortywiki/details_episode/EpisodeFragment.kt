package com.aura.project.rickandmortywiki.details_episode

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf

import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.Router
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.main_characters.CharacterAdapter

class EpisodeFragment private constructor(): Fragment(), CharacterAdapter.CharacterLoader, CharacterAdapter.OnCharClickListener {

    companion object {
        private const val CLINIC_ID_KEY = "clinicIdKey"
        fun newInstance(id: Long) = EpisodeFragment().apply {
            arguments = bundleOf(CLINIC_ID_KEY to id)
        }
    }

    private lateinit var _viewModel: EpisodeViewModel
    private lateinit var _title: TextView
    private lateinit var _shortTitle: TextView
    private lateinit var _date: TextView
    private lateinit var _adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.episode_fragment, container, false)
        view.apply {
            _title = findViewById(R.id.title)
            _shortTitle = findViewById(R.id.short_title)
            _date = findViewById(R.id.date)
        }
        _adapter = CharacterAdapter(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments!!.getLong(CLINIC_ID_KEY)
        val factory = EpisodeViewModel.Factory(id, activity!!.application)
        _viewModel = ViewModelProviders.of(this, factory).get(EpisodeViewModel::class.java)
    }

    override fun onCharClicked(character: Character) {
        (activity as Router).openCharacter(character.id)
    }

    override fun endReached() {
        //ignored
    }
}
