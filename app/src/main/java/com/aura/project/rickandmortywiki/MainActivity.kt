package com.aura.project.rickandmortywiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, MainShowFragment.newInstance())
            .commit()
    }
}
