package baccaro.lucas.com

import baccaro.lucas.com.db.DatabaseFactory
import baccaro.lucas.com.model.LoginRepository
import baccaro.lucas.com.model.PaymentRepository
import baccaro.lucas.com.model.RegisterRepository
import baccaro.lucas.com.model.UserRepository
import baccaro.lucas.com.plugins.getMembersRoute
import baccaro.lucas.com.plugins.loginRoute
import baccaro.lucas.com.plugins.paymentRoutes
import baccaro.lucas.com.plugins.purposeRoutes
import baccaro.lucas.com.plugins.registerRoute
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val registerRepository = RegisterRepository()
    val loginRepository = LoginRepository()
    val userRepository = UserRepository()
    val paymentRepository = PaymentRepository()

    routing {
        registerRoute(registerRepository)
        loginRoute(loginRepository)
        getMembersRoute(userRepository)
        paymentRoutes(paymentRepository)
        purposeRoutes(registerRepository)
    }
}
