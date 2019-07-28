package com.aura.project.rickandmortywiki.details_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.Router
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.databinding.CharacterDetailsFragmentBinding

//TODO: try to do it at viewPager
class CharacterDetailsFragment private constructor() : Fragment(),
    EpisodeAdapter.OnEpisodeClickListener {

    companion object {
        private var _INSTANCE: CharacterDetailsFragment? = null
        fun getInstance(char: Character): CharacterDetailsFragment =
            (_INSTANCE
                ?: CharacterDetailsFragment().also { _INSTANCE = it }).apply { _character = char }
    }

    private var _router: Router? = null

    //fast access to layout
    private val _layoutID = R.layout.character_details_fragment

    private lateinit var _binding: CharacterDetailsFragmentBinding
    private lateinit var _viewModel: CharacterDetailsViewModel
    private lateinit var _character: Character
    private lateinit var _adapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, _layoutID, container, false)
        _adapter = EpisodeAdapter(this)
        _binding.episodes.adapter = _adapter
        _binding.detailsLocation.setOnClickListener { _router?.openLocation(_character.location) }
        _binding.detailsOrigin.setOnClickListener { _router?.openOrigin(_character.origin) }
        _router = activity as Router
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = CharacterDetailsViewModel.Factory(_character)
        _viewModel = ViewModelProviders.of(this, factory).get(CharacterDetailsViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this
        _viewModel.apply {
            episodes.observe(this@CharacterDetailsFragment, episodesObserver)
        }
    }


    override fun onEpisodeClick(episode: String) {
        _router?.openEpisode(episode)
    }

    private val episodesObserver = Observer<List<Episode>> { episodes ->
        _adapter.list = episodes.map { "${it.seasonAndNum} ${it.title}" }
    }

    override fun onDestroy() {
        _adapter.onDestroy()
        super.onDestroy()
    }
}
