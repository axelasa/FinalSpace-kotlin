package com.asa.finalspace.network

import com.asa.finalspace.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {
    val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val allLocationsService:Locations = retrofit.create(Locations::class.java)
    val allQuotesService:Quotes = retrofit.create(Quotes::class.java)
    val allEpisodesService:Episodes = retrofit.create(Episodes::class.java)
    val allCharactersService:Characters = retrofit.create(Characters::class.java)
}