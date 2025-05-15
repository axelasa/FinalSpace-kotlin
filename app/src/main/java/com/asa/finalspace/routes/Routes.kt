package com.asa.finalspace.routes

import androidx.navigation.navArgument


object Routes {
    const val CHARACTER_LIST = "Character_list"
    const val EPISODES_SCREEN = "Episodes_Screen"

    const val CHARACTER_DETAILS = "character_details/{characterId}"
    const val EPISODE_DETAILS = "episode_details/{episodeId}"

    const val  ALL_LOCATIONS = "Locations"
    const val ALL_QUOTES = "Quotes"

    fun characterDetailsRoute(characterId:Int) = "character_details/$characterId"
   fun episodeDetailsRoute(episodeId:Int) = "episode_details/$episodeId"
//    fun characterDetailsRoute(characterId:Int) = "character_details/$characterId"
//    fun characterDetailsRoute(characterId:Int) = "character_details/$characterId"

    val characterDetailsArguments = listOf(
        navArgument(
        name = "characterId"
    ){
            type = androidx.navigation.NavType.IntType
        }
    )
    val episodeDetailsArguments = listOf(
        navArgument(
        name = "episodeId"
    ){
            type = androidx.navigation.NavType.IntType
        }
    )
}