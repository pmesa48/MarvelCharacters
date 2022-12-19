package com.pmesa48.marvelheroes.ui.characters.detail

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.pmesa48.marvelheroes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {

    companion object {
        const val CHARACTER_ID = "characterId"
    }

    private var characterId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        intent.extras?.let { characterId = it.getString(CHARACTER_ID) }
        characterId?.let { showFragment(it) }
        onBackPressedConfig()
    }

    private fun showFragment(it: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CharacterDetailFragment.newInstance(it))
            .commitNow()
    }

    private fun onBackPressedConfig() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }
}