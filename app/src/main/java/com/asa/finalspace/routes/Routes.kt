package com.asa.finalspace.routes

import androidx.navigation.navArgument


object Routes {
    const val CHARACTER_LIST = "Character_list"
    const val EPISODES_SCREEN = "Episodes_Screen"
    const val  ALL_LOCATIONS = "Locations"
    const val ALL_QUOTES = "Quotes"

    const val CHARACTER_DETAILS = "character_details/{characterId}"
    const val EPISODE_DETAILS = "episode_details/{episodeId}"
    const val LOCATION_DETAILS = "location_details/{locationId}"
    const val QUOTE_DETAILS = "quote_details/{quoteId}"



    fun characterDetailsRoute(characterId:Int) = "character_details/$characterId"
   fun episodeDetailsRoute(episodeId:Int) = "episode_details/$episodeId"
    fun locationDetailsRoute(locationId:Int) = "location_details/$locationId"
    fun quoteDetailsRoute(quoteId:Int) = "quote_details/$quoteId"

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

    val locationDetailsArguments = listOf(
        navArgument(
            name = "locationId"
        ){
            type = androidx.navigation.NavType.IntType
        }
    )

    val quoteDetailsArguments = listOf(
        navArgument(
            name = "quoteId"
        ){
            type = androidx.navigation.NavType.IntType
        }
    )
}