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
import com.aura.project.rickandmortywiki.databinding.CharacterDetailsFragmentBinding

//TODO: try to do it at viewPager
class CharacterDetailsFragment private constructor() : Fragment(), OnEpisodeClickListener {

    companion object {
        private var _INSTANCE: CharacterDetailsFragment? = null
        fun getInstance(char: Character): CharacterDetailsFragment =
            (_INSTANCE ?: CharacterDetailsFragment().also { _INSTANCE = it }).apply { _character = char }
    }

    private lateinit var _binding: CharacterDetailsFragmentBinding
    private lateinit var _viewModel: CharacterDetailsViewModel
    private lateinit var _character: Character

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.character_details_fragment, container, false)
        return _binding.root
    }

    /**
     * View model is partly binded, partly
     * I've done by this way to try _binding development's way
     * View Model is being initialized separately from UI for better performance
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
            //name.observe(this@CharacterDetailsFragment, nameObserver)
            //origin.observe(this@CharacterDetailsFragment, originObserver)
            //location.observe(this@CharacterDetailsFragment, locationObserver)
        }
    }
   /* private val nameObserver = Observer<String> { name ->
        _binding.detailsNameText.text = name
    }
    private val originObserver = Observer<String> { origin ->
        _binding.detailsOrigin.text = origin
    }
    private val locationObserver = Observer<String> { location ->
        _binding.detailsLocation.text = location
    }*/


    override fun onEpisodeClick(episode: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

}
