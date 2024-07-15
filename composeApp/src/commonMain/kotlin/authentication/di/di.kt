package authentication.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import authentication.data.local.AppDatabase
import authentication.data.local.DataPreferencesDao
import authentication.data.local.DataPreferencesRepository
import authentication.data.remote.AuthRepository
import authentication.data.remote.AuthService
import authentication.data.remote.LoginUseCase
import authentication.ui.AuthViewModel
import authentication.ui.DataPreferencesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
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
val authModule = module {
    factoryOf(::AuthRepository)
    factoryOf(::AuthService)
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTP
                    host = "10.0.2.2"
                    port = 8080
                }
            }
        }
    }
    factoryOf(::LoginUseCase)
    viewModelOf(::AuthViewModel)
}
expect val nativeModule: Module

fun initKoin(config: KoinAppDeclaration? = null) = run {
    startKoin {
        config?.invoke(this)
        modules(appModule, nativeModule, authModule)
    }
}