package com.pmesa48.marvelheroes.ui.characters.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmesa48.marvelheroes.model.Resource
import com.pmesa48.marvelheroes.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.pmesa48.marvelheroes.model.Character as Character

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> get() = _uiState

    fun getCharacter(characterId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            characterRepository.getCharacterById(characterId).collect {
                when(it) {
                    is Resource.Found -> _uiState.value = UiState.LoadCharacter(it.data)
                    else -> _uiState.value = UiState.NotFound("no")
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class LoadCharacter(val character: Character) : UiState()
        data class NotFound(val message: String?) : UiState()
    }

}
