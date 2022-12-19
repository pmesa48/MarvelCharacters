package com.pmesa48.marvelheroes.ui.characters.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmesa48.marvelheroes.R
import com.pmesa48.marvelheroes.R.*
import com.pmesa48.marvelheroes.databinding.FragmentCharacterListBinding
import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.ui.characters.detail.CharacterDetailActivity
import com.pmesa48.marvelheroes.ui.characters.list.CharacterListViewModel.UiState
import com.pmesa48.marvelheroes.ui.characters.list.CharacterListViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(layoutInflater)
        setupRecyclerView()
        setupSearchView()
        lifecycleScope.launchWhenStarted { viewModel.uiState.collect(::renderState) }
        return binding.root
    }

    private fun setupSearchView() {
        binding.fragmentHeroesListSearchBar.setOnQueryTextListener(
            object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.fragmentHeroesListSearchBar.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.query.value = newText.toString()
                    return true
                }

            })
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 3)
        adapter = CharacterAdapter { viewModel.onCharacterClicked(it.id) }
        binding.fragmentHeroesListRecyclerView.adapter = adapter
        binding.fragmentHeroesListRecyclerView.layoutManager = layoutManager
        binding.fragmentHeroesListRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.lastVisible.value =
                        if (layoutManager.findLastVisibleItemPosition() == -1) 0
                        else layoutManager.findLastVisibleItemPosition()
                }
            })
    }

    private fun renderState(uiState: UiState) {
        when(uiState) {
            is Loading -> loadingState()
            is LoadCharacters -> showCharactersState(uiState.characters)
            is NetworkError -> networkErrorState(uiState.message)
            is NoData -> emptyState()
            is Navigate -> navigateTo(uiState.characterId)
        }
    }

    private fun navigateTo(characterId: String) {
        val intent = Intent(context, CharacterDetailActivity::class.java).apply {
            putExtra(CharacterDetailActivity.CHARACTER_ID, characterId)
        }
        startActivity(intent)
    }

    private fun emptyState() {
        binding.viewEmptyState.container.visibility = View.VISIBLE
        binding.viewErrorState.container.visibility = View.GONE
        binding.progress.visibility = View.GONE
        binding.fragmentHeroesListRecyclerView.visibility = View.GONE
    }

    private fun networkErrorState(message: String?) {
        binding.viewEmptyState.container.visibility = View.GONE
        binding.viewErrorState.container.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
        binding.fragmentHeroesListRecyclerView.visibility = View.GONE

        binding.viewErrorState.stateText.text = message ?: getString(R.string.something_happened)
    }

    private fun showCharactersState(characters: List<Character>) {
        binding.fragmentHeroesListRecyclerView.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
        binding.viewErrorState.container.visibility = View.GONE
        binding.viewEmptyState.container.visibility = View.GONE

        adapter.submitList(characters)
    }

    private fun loadingState() {
        binding.fragmentHeroesListRecyclerView.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
        binding.viewErrorState.container.visibility = View.GONE
        binding.viewEmptyState.container.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterListFragment()
    }
}