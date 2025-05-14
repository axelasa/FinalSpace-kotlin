package com.asa.finalspace.routes

import androidx.navigation.navArgument

object Routes {

    const val CHARACTER_LIST = "Character_list"
    const val CHARACTER_DETAILS = "character_details/{characterId}"

    fun characterDetailsRoute(characterId:Int) = "character_details/$characterId"

    val characterDetailsArguments = listOf(
        navArgument(
        name = "characterId"
    ){
            type = androidx.navigation.NavType.IntType
        }
    )
}