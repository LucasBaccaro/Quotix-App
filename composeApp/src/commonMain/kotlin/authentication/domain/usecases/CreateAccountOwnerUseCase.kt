package authentication.domain.usecases

import authentication.data.remote.AuthRepository
import authentication.data.remote.dtos.OwnerRequest
import authentication.data.remote.dtos.OwnerResponse

class CreateAccountOwnerUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(ownerRequest: OwnerRequest): Result<OwnerResponse?> {
        return authRepository.createAccountOwner(ownerRequest)
    }
}