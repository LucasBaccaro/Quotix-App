package authentication.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String? = null,
    @SerialName("purposeName")
    val purposeName: String? = null,
    @SerialName("user")
    var user: User? = null,
    @SerialName("message")
    val message: String? = null,
)

@Serializable
data class User(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("role")
    val role: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("PurposeId")
    val PurposeId: Int
)

@Serializable
data class LoginRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class OwnerRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("role")
    val role: String,
    @SerialName("purposeName")
    val purposeName: String,
    @SerialName("feeAmount")
    val feeAmount: Double
)

@Serializable
data class MemberRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("role")
    val role: String,
    @SerialName("inviteCode")
    val inviteCode: String
)

@Serializable
data class MemberResponse(
    @SerialName("message")
    val message: String,
    @SerialName("userId")
    val userId: Int,
    @SerialName("purposeId")
    val purposeId: Int
)

@Serializable
data class OwnerResponse(
    @SerialName("message")
    val message: String,
    @SerialName("inviteCode")
    val inviteCode: String,
    @SerialName("userId")
    val userId: Int,
    @SerialName("purposeId")
    val purposeId: Int
)