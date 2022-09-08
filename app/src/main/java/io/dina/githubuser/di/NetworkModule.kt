package io.dina.githubuser.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.dina.githubuser.BuildConfig
import io.dina.githubuser.data.services.GithubService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = BuildConfig.BASE_URL

val NetworkModule = module {
    single<Interceptor>(named("loggingInterceptor")) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single<Interceptor>(named("chuckerInterceptor")) {
        ChuckerInterceptor.Builder(androidContext()).build()
    }

    single(named("githubTokenInterceptor")) {
        Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "token " + BuildConfig.TOKEN).build()
            chain.proceed(newRequest)
        }
    }

    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(get<Interceptor>(named("loggingInterceptor")))
                addInterceptor(get<Interceptor>(named("chuckerInterceptor")))
            }
            addInterceptor(get<Interceptor>(named("githubTokenInterceptor")))
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<GithubService> {
        get<Retrofit>().create(GithubService::class.java)
    }
}