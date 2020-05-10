package com.github.nafsan.searchuser


import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApp {
    private const val BASE_URL = "https://api.github.com/"
    val dispatcher = Dispatcher().apply {
        maxRequests = 10
    }
    val builder = OkHttpClient.Builder().apply {
        networkInterceptors()
        dispatcher(dispatcher)
    }
    var client = builder.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GITHUB_TCG_SERVICE: GithubTcgService = retrofit.create(GithubTcgService::class.java)

}

