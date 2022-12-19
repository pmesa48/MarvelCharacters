package com.pmesa48.marvelheroes.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.model.Resource
import com.pmesa48.marvelheroes.repository.character.CharacterRepository
import com.pmesa48.marvelheroes.repository.character.GetCharactersParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> get() = _uiState

    val lastVisible = MutableStateFlow(0)
    val query = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            lastVisible.combine(query) { last, query -> loadCharacters(query, last) }
                .stateIn(viewModelScope, SharingStarted.Eagerly, "")
        }
    }

    private suspend fun loadCharacters(query: String, lastVisible: Int) {
        characterRepository.load(GetCharactersParam( query, lastVisible)).collect {
            when(it) {
                is Resource.Found -> _uiState.value = UiState.LoadCharacters(it.data)
                is Resource.Error -> _uiState.value = UiState.NetworkError(it.message)
            }
        }
    }

    fun onCharacterClicked(id: String) {
        _uiState.value = UiState.Navigate(id)
    }

    sealed class UiState {
        object Loading : UiState()
        object NoData : UiState()
        data class Navigate(val characterId: String) : UiState()
        data class NetworkError(val message: String?) : UiState()
        data class LoadCharacters(val characters: List<Character>) : UiState()
    }

}
