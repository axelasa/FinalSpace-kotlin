package com.asa.finalspace.model.quotes

data class GetAllQuotesItem(
    val `by`: String,
    val character: String,
    val id: Int,
    val image: String,
    val quote: String
)