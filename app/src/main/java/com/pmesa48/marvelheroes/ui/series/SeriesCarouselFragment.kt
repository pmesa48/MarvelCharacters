package com.pmesa48.marvelheroes.ui.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmesa48.marvelheroes.databinding.FragmentSeriesCarouselBinding
import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.ui.comics.ComicCarouselFragment
import dagger.hilt.android.AndroidEntryPoint

private const val CHARACTER_ID = "characterId"

/**
 * Use the [ComicCarouselFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SeriesCarouselFragment : Fragment() {

    private val viewModel : SeriesCarouselViewModel by viewModels()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SeriesAdapter

    private var characterId: String? = null
    private var _binding : FragmentSeriesCarouselBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesCarouselBinding.inflate(inflater)
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
        characterId?.let { viewModel.getSeriesByCharacter(it) }
    }

    private fun showErrorState() {
        binding.carouselProgress.visibility = View.GONE
        binding.carouselTitleText.visibility = View.GONE
        binding.carouselRecycler.visibility = View.GONE
    }

    private fun showComicsState(comics: List<Series>) {
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
                    is SeriesCarouselViewModel.UiState.Show -> showComicsState(it.comics)
                    is SeriesCarouselViewModel.UiState.Loading -> showLoadingState()
                    is SeriesCarouselViewModel.UiState.Hide -> showErrorState()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = SeriesAdapter()
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
            SeriesCarouselFragment().apply {
                arguments = Bundle().apply { putString(CHARACTER_ID, characterId) }
            }
    }
}