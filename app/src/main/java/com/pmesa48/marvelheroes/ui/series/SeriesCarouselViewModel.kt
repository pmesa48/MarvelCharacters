package com.pmesa48.marvelheroes.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmesa48.marvelheroes.model.Resource
import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.repository.series.GetSeriesParam
import com.pmesa48.marvelheroes.repository.series.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesCarouselViewModel @Inject constructor(
    private val repository: SeriesRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> get() = _uiState

    val lastVisible = MutableStateFlow(0)


    fun getSeriesByCharacter(characterId: String) {
        viewModelScope.launch {
            lastVisible.collect { lastVisible ->
                repository.load(GetSeriesParam(characterId, lastVisible)).collect { resource ->
                    _uiState.value = when (resource) {
                        is Resource.Found ->
                            if(resource.data.isNotEmpty())
                                UiState.Show(resource.data)
                            else UiState.Hide
                        is Resource.Error -> UiState.Hide
                    }
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Hide : UiState()
        data class Show(val comics: List<Series>): UiState()
    }
}
