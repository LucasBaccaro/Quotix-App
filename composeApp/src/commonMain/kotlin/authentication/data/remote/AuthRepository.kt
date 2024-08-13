package authentication.data.remote

import authentication.data.remote.dtos.LoginRequest
import authentication.data.remote.dtos.LoginResponse
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.MemberResponse
import authentication.data.remote.dtos.OwnerRequest
import authentication.data.remote.dtos.OwnerResponse
import authentication.domain.models.DomainLoginResponse
import core.OperationResult

class AuthRepository(private val authService: AuthService) {
    suspend fun login(loginRequest: LoginRequest): OperationResult<LoginResponse?> {
        return authService.login(loginRequest)
    }
    suspend fun createAccountMember(memberRequest: MemberRequest): OperationResult<MemberResponse?> {
        return authService.createAccountMember(memberRequest)
    }

    suspend fun createAccountOwner(ownerRequest: OwnerRequest): OperationResult<OwnerResponse?> {
        return authService.createAccountOwner(ownerRequest)
    }

}