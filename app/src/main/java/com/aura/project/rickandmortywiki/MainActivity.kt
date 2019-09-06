package com.aura.project.rickandmortywiki

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.aura.project.rickandmortywiki.details_character.CharacterDetailsFragment
import com.aura.project.rickandmortywiki.details_episode.EpisodeFragment
import com.aura.project.rickandmortywiki.details_location.LocationFragment
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment

class MainActivity : AppCompatActivity(), Router {

    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var toolbarSearch: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        savedInstanceState ?: openFragment(AllCharFragment.newInstance())
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarSearch = toolbar.findViewById(R.id.toolbar_search_view)
    }

    override fun openCharacter(charId: Long) {
        openFragment(CharacterDetailsFragment.getInstance(charId), "detailChar $charId")
    }

    override fun openLocation(locationId: Long) {
        openFragment(LocationFragment.newInstance(locationId), "location $locationId")
    }

    override fun openEpisode(episodeId: Long) {
        openFragment(EpisodeFragment.newInstance(episodeId), "episode $episodeId")
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
