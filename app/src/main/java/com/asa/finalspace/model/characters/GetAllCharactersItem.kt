package com.asa.finalspace.model.characters

data class GetAllCharactersItem(
    val abilities: List<String>,
    val alias: List<String>,
    val gender: String,
    val hair: String,
    val id: Int,
    val img_url: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String
)