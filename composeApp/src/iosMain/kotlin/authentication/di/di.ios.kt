package authentication.di

import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder() }
}