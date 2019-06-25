package com.aura.project.rickandmortywiki

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        when (character.status){
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
        val name: TextView = view.findViewById(R.id.details_name_text)
        name.text = character.name
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
