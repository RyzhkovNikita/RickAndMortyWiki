package com.aura.project.rickandmortywiki.details_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.Router
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.databinding.CharacterDetailsFragmentBinding

class CharacterDetailsFragment : Fragment(),
    EpisodeAdapter.OnEpisodeClickListener {

    companion object {
        private const val CHAR_ID_KEY = "charIdKey"
        fun getInstance(id: Long): CharacterDetailsFragment =
            CharacterDetailsFragment().apply {
                arguments = bundleOf(CHAR_ID_KEY to id)
            }
    }

    private var _router: Router? = null

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
        _binding.apply {
            episodes.adapter = _adapter
            episodes.isNestedScrollingEnabled = false
            detailsLocation.setOnClickListener { _router?.openLocation(_character.location) }
            detailsOrigin.setOnClickListener { _router?.openOrigin(_character.origin) }
            error.findViewById<Button>(R.id.error_button).setOnClickListener { viewModel?.errorButtonClicked() }
        }
        _router = activity as Router
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments!!.getLong(CHAR_ID_KEY)
        val factory = CharacterDetailsViewModel.Factory(id, activity!!.application)
        _viewModel = ViewModelProviders.of(this, factory).get(CharacterDetailsViewModel::class.java)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this
        _viewModel.apply {
            episodes.observe(viewLifecycleOwner, episodesObserver)
            navToEpisode.observe(viewLifecycleOwner, navObserver)
        }
    }

    override fun onEpisodeClick(position: Int) {
        _viewModel.episodeClickAt(position)
    }

    private val episodesObserver = Observer<List<Episode>> { episodes ->
        _adapter.list = episodes.map { "${it.seasonAndNum} ${it.title}" }
    }

    private val navObserver = Observer<Episode> { episodeToNavigate ->
        episodeToNavigate?.let {
            _router?.openEpisode(episodeToNavigate.id)
            _viewModel.navigated()
        }
    }
}
