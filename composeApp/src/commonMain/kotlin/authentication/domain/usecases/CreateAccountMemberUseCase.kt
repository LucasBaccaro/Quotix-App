package authentication.domain.usecases

import authentication.data.remote.AuthRepository
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.MemberResponse

class CreateAccountMemberUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(memberRequest: MemberRequest): Result<MemberResponse?> {
        return authRepository.createAccountMember(memberRequest)
    }
}