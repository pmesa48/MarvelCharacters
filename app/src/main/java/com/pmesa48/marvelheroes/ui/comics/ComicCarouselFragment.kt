package com.pmesa48.marvelheroes.ui.comics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmesa48.marvelheroes.databinding.FragmentComicCarouselBinding
import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.ui.comics.ComicCarouselViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint

private const val CHARACTER_ID = "characterId"

/**
 * Use the [ComicCarouselFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ComicCarouselFragment : Fragment() {

    private val viewModel : ComicCarouselViewModel by viewModels()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ComicAdapter

    private var characterId: String? = null
    private var _binding : FragmentComicCarouselBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicCarouselBinding.inflate(inflater)
        setupRecyclerView()
        collectUiStates()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { characterId = it.getString(CHARACTER_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterId?.let { viewModel.getComicsByCharacter(it) }
    }

    private fun showErrorState() {
        binding.carouselProgress.visibility = View.GONE
        binding.carouselTitleText.visibility = View.GONE
        binding.carouselRecycler.visibility = View.GONE
    }

    private fun showComicsState(comics: List<Comic>) {
        binding.carouselProgress.visibility = View.GONE
        binding.carouselTitleText.visibility = View.VISIBLE
        binding.carouselRecycler.visibility = View.VISIBLE
        adapter.submitList(comics)
    }

    private fun showLoadingState() {
        binding.carouselProgress.visibility = View.VISIBLE
        binding.carouselTitleText.visibility = View.GONE
        binding.carouselRecycler.visibility = View.GONE
    }

    private fun collectUiStates() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is Show -> showComicsState(it.data)
                    is Loading -> showLoadingState()
                    is Hide -> showErrorState()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ComicAdapter()
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.carouselRecycler.layoutManager = linearLayoutManager
        binding.carouselRecycler.adapter = adapter
        binding.carouselRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.lastVisible.value =
                    if (linearLayoutManager.findLastVisibleItemPosition() == -1) 0
                    else linearLayoutManager.findLastVisibleItemPosition()
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(characterId: String) =
            ComicCarouselFragment().apply {
                arguments = Bundle().apply { putString(CHARACTER_ID, characterId) }
            }
    }
}