package authentication.data.remote.mappers

import authentication.data.remote.dtos.LoginResponse
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.MemberResponse
import authentication.data.remote.dtos.OwnerRequest
import authentication.data.remote.dtos.OwnerResponse
import authentication.data.remote.dtos.User
import authentication.domain.models.DomainLoginResponse
import authentication.domain.models.DomainMemberRequest
import authentication.domain.models.DomainMemberResponse
import authentication.domain.models.DomainOwnerRequest
import authentication.domain.models.DomainOwnerResponse
import authentication.domain.models.DomainUser

fun LoginResponse.toDomain(): DomainLoginResponse {
    return DomainLoginResponse(
        token = this.token,
        purposeName = this.purposeName,
        user = this.user?.toDomain(),
        message = this.message
    )
}

fun User.toDomain(): DomainUser {
    return DomainUser(
        id = this.id,
        email = this.email,
        role = this.role,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        purposeId = this.PurposeId
    )
}

fun OwnerRequest.toDomain(): DomainOwnerRequest {
    return DomainOwnerRequest(
        email = this.email,
        password = this.password,
        role = this.role,
        purposeName = this.purposeName,
        feeAmount = this.feeAmount
    )
}

fun MemberRequest.toDomain(): DomainMemberRequest {
    return DomainMemberRequest(
        email = this.email,
        password = this.password,
        role = this.role,
        inviteCode = this.inviteCode
    )
}

fun MemberResponse.toDomain(): DomainMemberResponse {
    return DomainMemberResponse(
        message = this.message,
        userId = this.userId,
        purposeId = this.purposeId
    )
}

fun OwnerResponse.toDomain(): DomainOwnerResponse {
    return DomainOwnerResponse(
        message = this.message,
        inviteCode = this.inviteCode,
        userId = this.userId,
        purposeId = this.purposeId
    )
}
