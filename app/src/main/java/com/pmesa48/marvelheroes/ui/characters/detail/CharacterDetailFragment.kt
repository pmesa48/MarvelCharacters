package com.pmesa48.marvelheroes.ui.characters.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.pmesa48.marvelheroes.R
import com.pmesa48.marvelheroes.databinding.FragmentCharacterDetailBinding
import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.ui.comics.ComicCarouselFragment
import com.pmesa48.marvelheroes.ui.characters.detail.CharacterDetailViewModel.UiState
import com.pmesa48.marvelheroes.ui.events.EventsCarouselFragment
import com.pmesa48.marvelheroes.ui.series.SeriesCarouselFragment
import dagger.hilt.android.AndroidEntryPoint


private const val CHARACTER_ID = "characterId"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private lateinit var navController: NavController
    private var characterId: String? = null

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { characterId = it.getString(CHARACTER_ID) }
        lifecycleScope.launchWhenStarted { viewModel.uiState.collect { renderState(it) } }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterId?.let {
            viewModel.getCharacter(it)
            renderFragments(it)
        }
    }



    private fun renderFragments(characterId: String) {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.comics_container,
                ComicCarouselFragment.newInstance(characterId))
            .replace(
                R.id.series_container,
                SeriesCarouselFragment.newInstance(characterId))
            .replace(
                R.id.events_container,
                EventsCarouselFragment.newInstance(characterId))
            .commitNow()
    }

    private fun renderState(uiState: UiState) {
        when(uiState) {
            is UiState.Loading -> renderLoading()
            is UiState.LoadCharacter -> renderCharacter(uiState.character)
            is UiState.NotFound -> renderNotFoundMessage(uiState.message)
        }
    }

    private fun renderNotFoundMessage(message: String?) {
        binding.fragmentCharacterDetailProgressBar.visibility = View.GONE
        binding.characterDetailGroup.visibility = View.GONE
        binding.viewErrorState.container.visibility = View.VISIBLE
        binding.viewErrorState.stateText.text = message ?: getString(R.string.something_happened)
    }

    private fun renderCharacter(character: Character) {
        binding.fragmentCharacterDetailProgressBar.visibility = View.GONE
        binding.viewErrorState.container.visibility = View.GONE
        binding.characterDetailGroup.visibility = View.VISIBLE
        binding.nameText.text = character.name
        binding.descriptionText.text = character.description
        Glide.with(binding.root.context)
            .load(character.background)
            .centerCrop()
            .into(binding.backgroundImage)
    }

    private fun renderLoading() {
        binding.fragmentCharacterDetailProgressBar.visibility = View.VISIBLE
        binding.characterDetailGroup.visibility = View.GONE
        binding.viewErrorState.container.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param characterId Parameter 1.
         * @return A new instance of fragment CharacterDetailFragment.
         */
        @JvmStatic
        fun newInstance(characterId: String) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply { putString(CHARACTER_ID, characterId) }
            }
    }
}