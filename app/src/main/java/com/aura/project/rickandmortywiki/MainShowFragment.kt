package com.aura.project.rickandmortywiki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment


class MainShowFragment : Fragment(), Callback {

    companion object {
        fun newInstance() = MainShowFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_show_fragment, container, false)
        return view
    }

    override fun onCharacterCardClicked(character: Character) {
        replace(CharacterDetailsFragment.newInstance(character), "detail ${character.id}")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState ?: replace(AllCharFragment.newInstance())
    }

    private fun replace(fragment: Fragment, tag: String = "") {
        val transaction = fragmentManager?.beginTransaction()
        if (tag.isNotEmpty())
            transaction?.addToBackStack(tag)
        transaction
            ?.replace(R.id.fragment_container, fragment)
            ?.commit()
    }

}
