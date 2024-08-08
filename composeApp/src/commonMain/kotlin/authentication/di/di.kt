package authentication.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import authentication.data.local.AppDatabase
import authentication.data.local.DataPreferencesDao
import authentication.data.local.DataPreferencesRepository
import authentication.data.remote.AuthRepository
import authentication.data.remote.AuthService
import authentication.domain.usecases.CreateAccountMemberUseCase
import authentication.domain.usecases.CreateAccountOwnerUseCase
import authentication.domain.usecases.LoginUseCase
import authentication.ui.AuthViewModel
import core.DataPreferencesViewModel
import core.getPlatformName
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single<DataPreferencesDao> {
        val dbBuilder = get<RoomDatabase.Builder<AppDatabase>>()
        dbBuilder.setDriver(BundledSQLiteDriver()).build().getDao()
    }
    factoryOf(::DataPreferencesRepository)
    viewModelOf(::DataPreferencesViewModel)
}

@OptIn(ExperimentalSerializationApi::class)
val authModule = module {
    factoryOf(::AuthRepository)
    factoryOf(::AuthService)
    factory {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    useAlternativeNames = false
                })
            }
            defaultRequest {
                header("Accept", "application/json")
                url {
                    protocol = URLProtocol.HTTP
                    host = getBaseUrl()
                    port = 3000
                }
            }
        }
    }
    factoryOf(::LoginUseCase)
    factoryOf(::CreateAccountMemberUseCase)
    factoryOf(::CreateAccountOwnerUseCase)
    viewModelOf(::AuthViewModel)
}

fun getBaseUrl(): String {
    return if (getPlatformName() == "Android") {
        "10.0.2.2"
    } else if (getPlatformName() == "iOS") {
        "127.0.0.1"
    } else {
        ""
    }
}

expect val nativeModule: Module

fun initKoin(config: KoinAppDeclaration? = null) = run {
    startKoin {
        config?.invoke(this)
        modules(appModule, nativeModule, authModule)
    }
}