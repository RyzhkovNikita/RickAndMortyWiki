package com.aura.project.rickandmortywiki.data.retrofit

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.CharacterPage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    fun getCharPage(@Query("page") page: Int): Call<CharacterPage>

    @GET("character/{id}")
    fun getCharById(@Path("id") id: Int): Call<Character>

    @GET("character/{ids}")
    fun getCharsById(@Path("ids") list: String): Call<List<Character>>

    companion object {
        @Volatile
        private var instance: ApiService? = null
        private val lock = Any()

        fun getInstance(): ApiService = instance
            ?: synchronized(lock) {
            instance
                ?: buildRetrofit().also { instance = it }
        }

        private fun buildRetrofit() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
            .create(ApiService::class.java)
    }
}