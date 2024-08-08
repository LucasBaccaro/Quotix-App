package authentication.domain.usecases

import authentication.data.remote.AuthRepository
import authentication.data.remote.dtos.LoginRequest
import authentication.data.remote.mappers.toDomain
import authentication.domain.models.DomainLoginResponse
import core.OperationResult

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): OperationResult<DomainLoginResponse?> {
        return when (val result = authRepository.login(loginRequest)) {
            is OperationResult.Success -> {
                OperationResult.Success(result.data?.toDomain())
            }

            is OperationResult.Error -> {
                OperationResult.Error(result.exception)
            }
        }
    }
}