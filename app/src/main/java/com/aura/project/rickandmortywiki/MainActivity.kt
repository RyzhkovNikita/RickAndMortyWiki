package com.aura.project.rickandmortywiki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Location
import com.aura.project.rickandmortywiki.data.Origin
import com.aura.project.rickandmortywiki.details_character.CharacterDetailsFragment
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: openFragment(AllCharFragment.newInstance())
    }

    override fun openCharacter(charId: Long) {
        openFragment(CharacterDetailsFragment.getInstance(charId), "detailChar $charId")
    }

    override fun openLocation(location: Location) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openOrigin(origin: Origin) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openEpisode(episode: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun openFragment(fragment: Fragment, tag: String = "") {
        val transaction = supportFragmentManager.beginTransaction()

        if (tag.isNotEmpty()) {
            transaction
                .replace(R.id.main_frame, fragment)
                .addToBackStack(tag)
        } else
            transaction.replace(R.id.main_frame, fragment)

        transaction.commit()
    }
}
