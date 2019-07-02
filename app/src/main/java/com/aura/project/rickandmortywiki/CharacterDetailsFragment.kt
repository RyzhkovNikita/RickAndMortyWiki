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


class CharacterDetailsFragment : Fragment() {

    companion object {
        private var _INSTANCE: CharacterDetailsFragment? = null
        fun getInstance(char: Character): CharacterDetailsFragment =
            (_INSTANCE ?: CharacterDetailsFragment().also { _INSTANCE = it }).apply { character = char }
    }

    private lateinit var binding: CharacterDetailsFragmentBinding
    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var character: Character

    private val nameObserver = Observer<String> { name ->
        binding.detailsNameText.text = name
    }
    private val originObserver = Observer<String> { origin ->
        binding.detailsOrigin.text = origin
    }
    private val locationObserver = Observer<String> { location ->
        binding.detailsLocation.text = location
    }
    private val imageObserver = Observer<String> { url ->
        ImageLoader
            .with(this.context!!)
            .from(url)
            .to(binding.detailsAvatarImage)
            .errorImage(R.drawable.char_error_avatar)
            .load()
    }
    private val genderObserver = Observer<String> { gender ->
        val genderSignDrawable: Drawable? = when (gender) {
            "Male" -> context?.getDrawable(R.drawable.male_sign)
            "Female" -> context?.getDrawable(R.drawable.female_sign)
            else -> null
        }
        binding.apply {
            val pixelDrawableSize = (detailsNameText.lineHeight * 0.8).toInt()
            genderSignDrawable?.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize)
            detailsNameText.setCompoundDrawables(null, null, genderSignDrawable, null)
            detailsNameText.compoundDrawablePadding = 8
        }
    }
    private val isDeadObserver = Observer<Boolean> { isDead ->
        binding.detailsDeadImage.visibility = if (isDead) View.VISIBLE else View.GONE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.character_details_fragment, container, false)
        return binding.root
    }

    /**
     * View model is partly binded, partly
     * I've done by this way to try binding development's way
     * View Model is being initialized separately from UI for better performance
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val characterDetailsViewModelFactory = CharacterDetailsViewModelFactory(character)
        viewModel = ViewModelProviders
            .of(this, characterDetailsViewModelFactory)
            .get(CharacterDetailsViewModel::class.java)
        binding.cDviewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.apply {
            setValuesByCharacter()
            //name.observe(this@CharacterDetailsFragment, nameObserver)
            image.observe(this@CharacterDetailsFragment, imageObserver)
            gender.observe(this@CharacterDetailsFragment, genderObserver)
            isDead.observe(this@CharacterDetailsFragment, isDeadObserver)
            //origin.observe(this@CharacterDetailsFragment, originObserver)
            //location.observe(this@CharacterDetailsFragment, locationObserver)
        }
    }

}
