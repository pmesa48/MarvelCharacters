package com.pmesa48.marvelheroes.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmesa48.marvelheroes.databinding.FragmentEventsCarouselBinding
import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.ui.comics.ComicCarouselFragment
import com.pmesa48.marvelheroes.ui.events.EventsCarouselViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint

private const val CHARACTER_ID = "characterId"

/**
 * Use the [ComicCarouselFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EventsCarouselFragment : Fragment() {

    private val viewModel : EventsCarouselViewModel by viewModels()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: EventAdapter

    private var characterId: String? = null
    private var _binding : FragmentEventsCarouselBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsCarouselBinding.inflate(inflater)
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

    private fun showComicsState(data: List<Event>) {
        binding.carouselProgress.visibility = View.GONE
        binding.carouselTitleText.visibility = View.VISIBLE
        binding.carouselRecycler.visibility = View.VISIBLE
        adapter.submitList(data)
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
                    is Show -> showComicsState(it.comics)
                    is Loading -> showLoadingState()
                    is Hide -> showErrorState()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = EventAdapter()
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
            EventsCarouselFragment().apply {
                arguments = Bundle().apply { putString(CHARACTER_ID, characterId) }
            }
    }
}