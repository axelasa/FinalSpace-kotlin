package com.asa.finalspace.repository

import com.asa.finalspace.model.characters.GetAllCharacters
import com.asa.finalspace.model.characters.GetAllCharactersItem
import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.network.Characters
import retrofit2.HttpException

interface AllCharacterRepository {
    suspend fun fetchAllCharacters():TaskResults<GetAllCharacters>
}

class AllCharactersRepositoryImpl(private val allCharactersService:Characters):AllCharacterRepository{
    override suspend fun fetchAllCharacters(): TaskResults<GetAllCharacters> {
        try{
            val response = allCharactersService.getAllCharacters()
            if (!response.isSuccessful){
                return TaskFail(
                    response.message(),
                    HttpException(response),
                )
            }
            val data = response.body()
            println("HERE IS THE DATA $data")
            return TaskSuccess(data!!)
        }catch (e:Exception){
            return TaskFail(e.message?: "Something Went Wrong",e)
        }
    }

}