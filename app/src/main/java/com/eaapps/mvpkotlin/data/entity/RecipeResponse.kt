package com.eaapps.mvpkotlin.data.entity

data class RecipeItem(
    val recipe: Recipe
)

data class RecipeEntities(
    val count: Int,
    val recipes: List<Recipe>
)

data class Recipe(
    val __v: Int = -1,
    val _id: String = "",
    val image_url: String,
    val ingredients: List<String> = emptyList(),
    val publisher: String = "",
    val publisher_url: String = "",
    val recipe_id: String,
    val social_rank: Double = -1.0,
    val source_url: String,
    val title: String,
    var favorited: Boolean

)


