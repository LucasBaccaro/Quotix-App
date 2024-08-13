package authentication.domain.usecases

import authentication.data.remote.AuthRepository
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.MemberResponse
import authentication.domain.models.DomainLoginResponse
import core.OperationResult

class CreateAccountMemberUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(memberRequest: MemberRequest): OperationResult<MemberResponse?> {
        return authRepository.createAccountMember(memberRequest)
    }
}