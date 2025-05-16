package com.asa.finalspace.repository

import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.model.episodes.GetAllEpisodes
import com.asa.finalspace.model.locations.GetAllLocations
import com.asa.finalspace.network.Locations
import retrofit2.HttpException

interface AllLocationsRepository {
    suspend fun fetchAllLocations():TaskResults<GetAllLocations>
}

class AllLocationsRepositoryImpl(private val allLocationService:Locations):AllLocationsRepository{
    override suspend fun fetchAllLocations(): TaskResults<GetAllLocations> {
        try {
            val  response = allLocationService.getAllLocations()
            if(!response.isSuccessful){
               return TaskFail(
                    response.message(),
                    HttpException(response)
                )
            }
            val data = response.body()
            println("HERE IS THE DATA ${data}")
            return TaskSuccess(data!!)

        }catch (e:Exception){
           return TaskFail(e.message ?: "Something Went Wrong",e)
        }
    }

}