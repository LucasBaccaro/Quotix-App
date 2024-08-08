package authentication.data.remote

import authentication.data.remote.dtos.LoginRequest
import authentication.data.remote.dtos.LoginResponse
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.MemberResponse
import authentication.data.remote.dtos.OwnerRequest
import authentication.data.remote.dtos.OwnerResponse
import core.OperationResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthService(private val client: HttpClient) {
    suspend fun login(loginRequest: LoginRequest): OperationResult<LoginResponse?> {
        return try {
            val response: LoginResponse = client.post("/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    LoginRequest(
                    email = loginRequest.email,
                    password = loginRequest.password,
                )
                )
            }.body()
            OperationResult.Success(response)
        } catch (e: Exception) {
            OperationResult.Error("Api Error ${e.message}}")
        }
    }
    suspend fun createAccountMember(memberRequest: MemberRequest): Result<MemberResponse?> {
        return try {
            val response: MemberResponse = client.post("/api/users") {
                contentType(ContentType.Application.Json)
                setBody(memberRequest)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.failure(e)
        }
    }

    suspend fun createAccountOwner(ownerRequest: OwnerRequest): Result<OwnerResponse?> {
        return try {
            val response: OwnerResponse = client.post("/api/users") {
                contentType(ContentType.Application.Json)
                setBody(ownerRequest)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.failure(e)
        }
    }
}

