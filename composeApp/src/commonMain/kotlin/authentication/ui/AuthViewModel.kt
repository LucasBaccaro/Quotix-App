package authentication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.LoginRequest
import auth.User
import authentication.data.remote.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            val result = loginUseCase(loginRequest)
            _uiState.value = uiState.value.copy(isLoading = false, user = result.getOrNull())
        }
    }

    /* fun createAccount(username: String, password: String) {
         viewModelScope.launch {
             _uiState.value = uiState.value.copy(isLoading = true)
             val result = createAccountUseCase(username, password)
             _uiState.value = uiState.value.copy(isLoading = false, user = result.getOrNull())
         }
     }*/
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null
)