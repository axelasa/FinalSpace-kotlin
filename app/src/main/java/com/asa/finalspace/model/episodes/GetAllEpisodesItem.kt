package com.asa.finalspace.model.episodes

data class GetAllEpisodesItem(
    val air_date: String,
    val characters: List<String>,
    val director: String,
    val id: Int,
    val img_url: String,
    val name: String,
    val writer: String
)