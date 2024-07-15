package authentication.data.remote

import auth.LoginRequest
import auth.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthService(private val client: HttpClient) {
    suspend fun login(loginRequest: LoginRequest): Result<User?> {
        return try {
            val response: User = client.post("/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class AuthRepository(private val authService: AuthService) {
    suspend fun login(loginRequest: LoginRequest): Result<User?> {
        return authService.login(loginRequest)
    }
}

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<User?> {
        return authRepository.login(loginRequest)
    }
}


/*suspend fun createAccount(username: String, password: String): Result<User> {
    return authService.createAccount(username, password)
}*/

/*class CreateAccountUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.createAccount(username, password)
    }
}*/

