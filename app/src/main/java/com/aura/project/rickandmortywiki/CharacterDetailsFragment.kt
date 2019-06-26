package com.aura.project.rickandmortywiki

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.data.Character


class CharacterDetailsFragment(val character: Character) : Fragment() {

    companion object {
        fun newInstance(character: Character) = CharacterDetailsFragment(character)
    }

    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_details_fragment, container, false)
        val deadImageView: ImageView = view.findViewById(R.id.details_dead_image)
        when (character.status) {
            "Dead" -> deadImageView.visibility = View.VISIBLE
            else -> deadImageView.visibility = View.GONE
        }
        val avatar: ImageView = view.findViewById(R.id.details_avatar)
        ImageLoader
            .with(this.context!!)
            .from(character.image)
            .to(avatar)
            .errorImage(R.drawable.char_error_avatar)
            .load()
        val nameTv: TextView = view.findViewById(R.id.details_name_text)
        nameTv.text = character.name
        val genderSignDrawable: Drawable? = when (character.gender) {
            "Male" -> context?.getDrawable(R.drawable.male_sign)
            "Female" -> context?.getDrawable(R.drawable.female_sign)
            else -> null
        }
        val pixelDrawableSize = (nameTv.lineHeight * 0.8).toInt()
        genderSignDrawable?.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize)
        nameTv.setCompoundDrawables(null, null, genderSignDrawable, null)
        nameTv.compoundDrawablePadding = 8
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
