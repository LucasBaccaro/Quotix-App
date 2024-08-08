package authentication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authentication.data.remote.dtos.LoginRequest
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.OwnerRequest
import authentication.domain.models.DomainUser
import authentication.domain.usecases.CreateAccountMemberUseCase
import authentication.domain.usecases.CreateAccountOwnerUseCase
import authentication.domain.usecases.LoginUseCase
import core.OperationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val createAccountMemberUseCase: CreateAccountMemberUseCase,
    private val createAccountOwnerUseCase: CreateAccountOwnerUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = loginUseCase(loginRequest)
            _uiState.update {
                when (result) {
                    is OperationResult.Success -> it.copy(user = result.data?.user, isLoading = false, error = null)
                    is OperationResult.Error -> it.copy(isLoading = false, error = result.exception, user = null)
                }
            }
        }
    }

    fun createAccountMember(memberRequest: MemberRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = createAccountMemberUseCase(memberRequest)
            _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
        }
    }

    fun createAccountOwner(ownerRequest: OwnerRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = createAccountOwnerUseCase(ownerRequest)
            _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
        }
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: DomainUser? = null,
    val error: String? = null
)