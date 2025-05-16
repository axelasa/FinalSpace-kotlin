package com.asa.finalspace.repository

import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.model.quotes.GetAllQuotes
import com.asa.finalspace.network.Quotes
import retrofit2.HttpException

interface AllQuotesRepository {
    suspend fun fetchAllQuotes():TaskResults<GetAllQuotes>
}

class AllQuotesRepositoryImpl(private val quotesService:Quotes):AllQuotesRepository{
    override suspend fun fetchAllQuotes(): TaskResults<GetAllQuotes> {
        val response = quotesService.getAllQuotes()
        try {
            if (!response.isSuccessful){
                return TaskFail(
                    response.message(),
                    HttpException(response)
                )
            }
            val data = response.body()
            return TaskSuccess(data!!)
        }catch(e:Exception){
            return TaskFail(e.message ?: "Something Went Wrong",e)
        }
    }

}