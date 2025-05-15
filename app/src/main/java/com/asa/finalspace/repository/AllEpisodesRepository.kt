package com.asa.finalspace.repository

import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.model.episodes.GetAllEpisodes
import com.asa.finalspace.network.Episodes
import retrofit2.HttpException

interface AllEpisodesRepository {
    suspend fun getAllEpisodes():TaskResults<GetAllEpisodes>
}

class  AllEpisodesRepositoryImpl(private val allEpisodesService: Episodes):AllEpisodesRepository{
    override suspend fun getAllEpisodes(): TaskResults<GetAllEpisodes> {
       try{
           val response = allEpisodesService.getAllEpisodes()
           if(!response.isSuccessful){
               return TaskFail(
                   response.message(),
                   HttpException(response)
               )
           }
           val data = response.body()
           println("HERE IS THE DATA $data")
           return TaskSuccess(data!!)

       }catch (e:Exception){
           return TaskFail(e.message ?: "Something went Wrong",e)
       }
    }
}