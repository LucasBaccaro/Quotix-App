package baccaro.lucas.com.plugins

import auth.LoginRequest
import auth.PaymentRequest
import auth.RegisterMemberRequest
import auth.RegisterOwnerRequest
import baccaro.lucas.com.model.LoginRepository
import baccaro.lucas.com.model.PaymentRepository
import baccaro.lucas.com.model.RegisterRepository
import baccaro.lucas.com.model.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.getMembersRoute(userRepository: UserRepository) {
    get("/purpose/{purposeId}/users") {
        val purposeId = call.parameters["purposeId"]?.toIntOrNull()
            ?: throw IllegalArgumentException("Purpose ID is required")
        val users = userRepository.getUsersByPurpose(purposeId)
        call.respond(HttpStatusCode.OK, users)
    }
}

fun Route.loginRoute(loginRepository: LoginRepository) {
    post("/login") {
        val request = call.receive<LoginRequest>()
        val userWithPurpose = loginRepository.loginUser(request.email, request.password)

        if (userWithPurpose != null) {
            call.respond(HttpStatusCode.OK, userWithPurpose)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }
}

fun Route.registerRoute(registerRepository: RegisterRepository) {
    route("/register") {
        post("/owner") {
            val request = call.receive<RegisterOwnerRequest>()
            val ownerId = registerRepository.registerOwner(
                request.email,
                request.password,
                request.purposeName,
                request.cbu
            )
            call.respond(HttpStatusCode.Created, ownerId)
        }

        post("/member") {
            val request = call.receive<RegisterMemberRequest>()
            val memberId = registerRepository.registerMember(
                request.email,
                request.password,
                request.inviteCode
            )
            call.respond(HttpStatusCode.Created, memberId)
        }
    }
}

fun Route.paymentRoutes(paymentRepository: PaymentRepository) {
    post("/payments") {
        val paymentRequest = call.receive<PaymentRequest>()
        val payment = paymentRepository.addPayment(paymentRequest)
        call.respond(HttpStatusCode.Created, payment)
    }
}

fun Route.purposeRoutes(registerRepository: RegisterRepository) {

    get("/purpose/{inviteCode}/cbu") {
        val inviteCode = call.parameters["inviteCode"]
            ?: throw IllegalArgumentException("Invite code is required")

        val cbu = registerRepository.getCbuByInviteCode(inviteCode)

        if (cbu != null) {
            call.respond(HttpStatusCode.OK, cbu)
        } else {
            call.respond(HttpStatusCode.NotFound, "Purpose not found for invite code: $inviteCode")
        }
    }
}