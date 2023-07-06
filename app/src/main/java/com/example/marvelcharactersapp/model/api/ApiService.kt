package com.example.marvelcharactersapp.model.api

import android.provider.Settings
import com.example.marvelcharactersapp.BuildConfig
import com.example.marvelcharactersapp.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ApiService above the Marvel Api
// This layer provides us with the actual api to communicate with the backend api
object ApiService {
    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    private fun getRetrofit(): Retrofit {
        val apiSecretKey = BuildConfig.SECRET_KEY
        val apiKey = BuildConfig.MARVEL_KEY
        val ts = System.currentTimeMillis().toString()
        val hash = getHash(ts, apiSecretKey, apiKey)

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder().addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey).addQueryParameter("hash", hash).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
//        Here, we are retrieving the request, adding parameters to the request and then continuing the request with the chain provided with the request

        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor).build()

//        Returning the retrofit object
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)
}