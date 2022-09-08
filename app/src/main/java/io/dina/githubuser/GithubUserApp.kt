package io.dina.githubuser

import android.app.Application
import io.dina.githubuser.di.DomainModule
import io.dina.githubuser.di.LocalModule
import io.dina.githubuser.di.NetworkModule
import io.dina.githubuser.di.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubUserApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GithubUserApp)
            modules(NetworkModule, LocalModule, DomainModule, UiModule)
        }
    }
}