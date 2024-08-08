package core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authentication.data.local.DataPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataPreferencesViewModel(private val preferencesRepository: DataPreferencesRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    init {
        onUiReady()
    }
    private fun onUiReady() {
        viewModelScope.launch {
            preferencesRepository.isOnboardingCompleted.collect { completed ->
                _state.update { it.copy(isLoading = false, isOnboardingCompleted = completed) }
            }
        }
    }
    fun completeOnboarding() {
        viewModelScope.launch {
            preferencesRepository.setOnboardingCompleted(true)
            _state.update { it.copy(isOnboardingCompleted = true) }
        }
    }
}

data class UiState(
    val isLoading: Boolean = true,
    var isOnboardingCompleted: Boolean = false
)