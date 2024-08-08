package authentication.domain.models

data class DomainUser(
    val id: Int,
    val email: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String,
    val purposeId: Int
)

data class DomainLoginResponse(
    val token: String?,
    val purposeName: String?,
    val user: DomainUser?,
    val message: String?
)

data class DomainOwnerRequest(
    val email: String,
    val password: String,
    val role: String,
    val purposeName: String,
    val feeAmount: Double
)

data class DomainMemberRequest(
    val email: String,
    val password: String,
    val role: String,
    val inviteCode: String
)

data class DomainMemberResponse(
    val message: String,
    val userId: Int,
    val purposeId: Int
)

data class DomainOwnerResponse(
    val message: String,
    val inviteCode: String,
    val userId: Int,
    val purposeId: Int
)