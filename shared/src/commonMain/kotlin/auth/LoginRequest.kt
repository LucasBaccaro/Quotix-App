package auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterOwnerRequest(
    val email: String,
    val password: String,
    val purposeName: String,
    val cbu: String
)

@Serializable
data class RegisterMemberRequest(val email: String, val password: String, val inviteCode: String)

@Serializable
data class LoginRequest(val email: String, val password: String)
enum class UserRole {
    OWNER,
    MEMBER
}

@Serializable
data class Payment(
    val id: Int,
    val memberId: Int,
    val purposeId: Int,
    val amount: Double,
    val date: String
)

@Serializable
data class PaymentRequest(
    val memberId: Int,
    val purposeId: Int,
    val amount: Double,
    val date: String
)


@Serializable
data class User(
    val id: Int? = null,
    val email: String? = null,
    val role: UserRole? = null,
    val inviteCode: String? = null,
    val payments: List<Payment> = emptyList(),
    val cbu: String? = null
)


@Serializable
data class LoginResponse(val login: Boolean, val user: User?)

@Serializable
data class RegisterOwnerResponse(
    val ownerId: Int,
    val inviteCode: String
)

@Serializable
data class RegisterMemberResponse(
    val memberId: Int
)







