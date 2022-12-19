package com.pmesa48.marvelheroes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmesa48.marvelheroes.R
import com.pmesa48.marvelheroes.ui.characters.list.CharacterListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CharacterListFragment.newInstance())
            .commitNow()
    }
}

