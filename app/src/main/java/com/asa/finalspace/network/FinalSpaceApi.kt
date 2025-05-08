package com.asa.finalspace.network
import com.asa.finalspace.allEpisodes
import com.asa.finalspace.allCharacters
import com.asa.finalspace.allLQuotes
import com.asa.finalspace.allLocations
import com.asa.finalspace.model.characters.GetAllCharacters
import com.asa.finalspace.model.characters.GetAllCharactersItem
import com.asa.finalspace.model.episodes.GetAllEpisodes
import com.asa.finalspace.model.episodes.GetAllEpisodesItem
import com.asa.finalspace.model.locations.GetAllLocations
import com.asa.finalspace.model.locations.GetAllLocationsItem
import retrofit2.Response
import retrofit2.http.GET

interface Characters {
    @GET(allCharacters)
    suspend fun getAllCharacters():Response<GetAllCharactersItem>
}

interface Episodes{
    @GET(allEpisodes)
    suspend fun getAllEpisodes():Response<GetAllEpisodesItem>
}

interface Locations{
    @GET(allLocations)
    suspend fun getAllLocations():Response<GetAllLocationsItem>
}

interface Quotes{
    @GET(allLQuotes)
    suspend fun  getAllQuotes():Response<GetAllEpisodesItem>
}