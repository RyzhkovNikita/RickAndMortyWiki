package com.aura.project.rickandmortywiki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment

class MainActivity : AppCompatActivity(), OnCharacterClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: addFragment(AllCharFragment.newInstance())
    }

    override fun onCharacterCardClick(character: Character) {
        addFragment(CharacterDetailsFragment.getInstance(character), "detailChar ${character.id}")
    }

    //TODO: navigation from jetpack
    private fun addFragment(fragment: Fragment, tag: String = "") {
        val transaction = supportFragmentManager.beginTransaction()

        if (tag.isNotEmpty()) {
            transaction
                .add(R.id.main_frame, fragment)
                .addToBackStack(tag)
        } else
            transaction.replace(R.id.main_frame, fragment)

        transaction.commit()
    }
}
