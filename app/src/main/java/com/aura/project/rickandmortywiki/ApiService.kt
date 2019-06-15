package com.aura.project.rickandmortywiki

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("character/")
    fun getCharPageCall(): Call<CharacterPage>

    companion object {
        @Volatile
        private var instance: ApiService? = null
        private val lock = Any()

        fun getInstance(): ApiService = instance ?: synchronized(lock) {
            instance ?: buildRetrofit().also { instance = it }
        }

        private fun buildRetrofit() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
            .create(ApiService::class.java)
    }
}