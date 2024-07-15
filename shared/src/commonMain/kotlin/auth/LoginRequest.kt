package auth

import kotlinx.serialization.Serializable

enum class UserRole {
    OWNER,
    MEMBER
}
@Serializable
data class RegisterOwnerRequest(
    val email: String,
    val password: String,
    val dni: String,
    val inviteCode: String,
    val cbu: String,
    val chargeAmount: Double,
    val entityName: String
)

@Serializable
data class RegisterMemberRequest(
    val email: String,
    val password: String,
    val inviteCode: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class PaymentRequest(
    val inviteCode: String,
    val amount: Double,
    val date: String
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

@Serializable
data class UserResponse(
    val id: Int,
    val email: String,
    val role: UserRole
)

@Serializable
data class PaymentResponse(
    val memberId: Int,
    val purposeId: Int,
    val amount: Double,
    val date: String
)

@Serializable
data class MemberWithPaymentResponse(
    val memberId: Int,
    val email: String,
    val payments: List<PaymentResponse>
)









