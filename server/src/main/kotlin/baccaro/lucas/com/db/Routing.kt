package baccaro.lucas.com.db

import auth.ApiResponse
import auth.LoginRequest
import auth.RegisterMemberRequest
import auth.RegisterOwnerRequest
import auth.UserResponse
import auth.UserRole
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.registerOwner() {
    post("/register/owner") {
        val request = call.receive<RegisterOwnerRequest>()
        val existingUser = transaction {
            Users.select { Users.email eq request.email }
                .map { it[Users.id].value }
                .singleOrNull()
        }
        val existingInviteCode = transaction {
            Purposes.select { Purposes.inviteCode eq request.inviteCode }
                .map { it[Purposes.id].value }
                .singleOrNull()
        }
        if (existingInviteCode != null) {
            call.respond(
                ApiResponse(
                    false,
                    "Invite code already exists",
                    null
                )
            )
            return@post
        }
        if (existingUser != null) {
            call.respond(
                ApiResponse(
                    false,
                    "User already exists",
                    null
                )
            )
            return@post
        }
        val userId = transaction {
            Users.insertAndGetId {
                it[email] = request.email
                it[password] = request.password
                it[role] = UserRole.OWNER
                it[dni] = request.dni
                it[inviteCode] = request.inviteCode
            }
        }

        transaction {
            Purposes.insert {
                it[name] = request.entityName
                it[ownerId] = userId
                it[inviteCode] = request.inviteCode
                it[cbu] = request.cbu
                it[chargeAmount] = request.chargeAmount.toBigDecimal()
            }
        }
        call.respond(
            ApiResponse(
                true,
                "Owner registered successfully",
                UserResponse(userId.value, request.email, UserRole.OWNER)
            )
        )
    }
}

fun Route.registerMember() {
    post("/register/member") {
        val request = call.receive<RegisterMemberRequest>()

        val existingUser = transaction {
            Users.select { Users.email eq request.email }
                .map { it[Users.id].value }
                .singleOrNull()
        }
        val inviteCodeNotExist = transaction {
            Purposes.select { Purposes.inviteCode eq request.inviteCode }
                .map { it[Purposes.id].value }
                .singleOrNull()
        }
        if (existingUser != null) {
            call.respond(
                ApiResponse(
                    false,
                    "User already exists",
                    null
                )
            )
            return@post
        } else if (inviteCodeNotExist == null) {
            call.respond(
                ApiResponse(
                    false,
                    "Invalid invite code",
                    null
                )
            )
            return@post
        } else {
            val userId = transaction {
                Users.insertAndGetId {
                    it[email] = request.email
                    it[password] = request.password
                    it[role] = UserRole.MEMBER
                    it[inviteCode] = request.inviteCode
                }
            }
            call.respond(
                ApiResponse(
                    true,
                    "Member registered successfully",
                    UserResponse(userId.value, request.email, UserRole.MEMBER)
                )
            )
        }
    }
}

fun Route.login() {
    post("/login") {
        val request = call.receive<LoginRequest>()

        val user = transaction {
            (Users innerJoin Purposes)
                .slice(Users.id, Users.role, Users.inviteCode, Purposes.cbu, Purposes.chargeAmount)
                .select {
                    (Users.email eq request.email) and
                            (Users.password eq request.password) and
                            (Users.inviteCode eq Purposes.inviteCode)
                }
                .map {
                    UserResponse(
                        it[Users.id].value,
                        request.email,
                        it[Users.role],
                        it[Users.inviteCode],
                        it[Purposes.cbu],
                        it[Purposes.chargeAmount].toDouble()
                    )
                }
                .singleOrNull()
        }

        if (user != null) {
            call.respond(
                ApiResponse(
                    true,
                    "Login successful",
                    user
                )
            )
        } else {
            call.respond(
                HttpStatusCode.Unauthorized,
                ApiResponse<Unit>(false, "Invalid credentials")
            )
        }
    }
}