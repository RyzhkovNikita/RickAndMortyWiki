package com.aura.project.rickandmortywiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment

class MainActivity : AppCompatActivity(), Callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?: replace(AllCharFragment.newInstance())
    }

    override fun onCharacterCardClicked(character: Character) {
        replace(CharacterDetailsFragment.getInstance(character), "detailChar ${character.id}")
    }

    private fun replace(fragment: Fragment, tag: String = "") {
        val transaction = supportFragmentManager?.beginTransaction()
        if (tag.isNotEmpty())
            transaction?.addToBackStack(tag)
        transaction
            ?.replace(R.id.main_frame, fragment)
            ?.commit()
    }
}
