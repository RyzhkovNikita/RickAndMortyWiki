package com.aura.project.rickandmortywiki

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.databinding.CharacterDetailsFragmentBinding


class CharacterDetailsFragment(val character: Character) : Fragment() {

    companion object {
        fun newInstance(char: Character) = CharacterDetailsFragment(char)
    }

    private lateinit var binding: CharacterDetailsFragmentBinding
    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.character_details_fragment, container, false)
        binding.characterData = character.copy()
        binding.apply {
            
            when (character.status) {
                "Dead" -> detailsDeadImage.visibility = View.VISIBLE
                else -> detailsDeadImage.visibility = View.GONE
            }

            ImageLoader
                .with(this@CharacterDetailsFragment.context!!)
                .from(character.image)
                .to(detailsAvatarImage)
                .errorImage(R.drawable.char_error_avatar)
                .load()

            val genderSignDrawable: Drawable? = when (character.gender) {
                "Male" -> context?.getDrawable(R.drawable.male_sign)
                "Female" -> context?.getDrawable(R.drawable.female_sign)
                else -> null
            }
            val pixelDrawableSize = (detailsNameText.lineHeight * 0.8).toInt()
            genderSignDrawable?.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize)
            detailsNameText.setCompoundDrawables(null, null, genderSignDrawable, null)
            detailsNameText.compoundDrawablePadding = 8
        }
        
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
