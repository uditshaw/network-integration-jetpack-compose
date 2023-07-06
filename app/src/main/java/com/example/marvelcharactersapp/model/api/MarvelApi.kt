package com.example.marvelcharactersapp.model.api

import com.example.marvelcharactersapp.model.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")    // "characters" is the end point
    fun getCharacters(@Query("nameStartsWith") name: String): Call<CharactersApiResponse>
//    Query of "nameStartsWith" which contains the parameter name that will return a call of type CharApiResponse already defined in characters.kt file
}