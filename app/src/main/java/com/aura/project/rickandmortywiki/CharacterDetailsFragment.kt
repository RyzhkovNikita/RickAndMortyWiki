package com.aura.project.rickandmortywiki

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.databinding.CharacterDetailsFragmentBinding

//TODO: try to do it at viewPager
class CharacterDetailsFragment private constructor() : Fragment(), EpisodeAdapter.OnEpisodeClickListener {

    companion object {
        private var _INSTANCE: CharacterDetailsFragment? = null
        fun getInstance(char: Character): CharacterDetailsFragment =
            (_INSTANCE ?: CharacterDetailsFragment().also { _INSTANCE = it }).apply { _character = char }
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

    /**
     * View model is binded partly, observed partly
     * I've done by this way to try binding development's way
     * I usually observe the whole viewModel
     * View Model is being initialized separately from views for better readability
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)

        _viewModel setupWith _character

        _binding.cDviewModel = _viewModel
        _binding.lifecycleOwner = this
        _viewModel.apply {
            image.observe(this@CharacterDetailsFragment, imageObserver)
            gender.observe(this@CharacterDetailsFragment, genderObserver)
            isDead.observe(this@CharacterDetailsFragment, isDeadObserver)
            episodes.observe(this@CharacterDetailsFragment, episodesObserver)
        }
    }


    override fun onEpisodeClick(episode: String) {
        _router?.openEpisode(episode)
    }

    private val episodesObserver = Observer<List<Episode>> { episodes ->
        _adapter.list = episodes.map { "${it.seasonAndNum} ${it.title}" }
    }

    private val imageObserver = Observer<String> { url ->
        ImageLoader
            .with(this.context!!)
            .from(url)
            .to(_binding.detailsAvatarImage)
            .errorImage(R.drawable.char_error_avatar)
            .load()
    }
    private val genderObserver = Observer<String> { gender ->
        val genderSignDrawable: Drawable? = when (gender) {
            "Male" -> context?.getDrawable(R.drawable.male_sign)
            "Female" -> context?.getDrawable(R.drawable.female_sign)
            else -> null
        }
        _binding.apply {
            val pixelDrawableSize = (detailsNameText.lineHeight * 0.8).toInt()
            genderSignDrawable?.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize)
            detailsNameText.setCompoundDrawables(null, null, genderSignDrawable, null)
            detailsNameText.compoundDrawablePadding = 8
        }
    }
    private val isDeadObserver = Observer<Boolean> { isDead ->
        _binding.detailsDeadImage.visibility = if (isDead) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        _adapter.onDestroy()
        super.onDestroy()
    }
}
