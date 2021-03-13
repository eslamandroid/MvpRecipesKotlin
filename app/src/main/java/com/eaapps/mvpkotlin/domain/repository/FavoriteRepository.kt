package com.eaapps.mvpkotlin.domain.repository

import com.eaapps.mvpkotlin.data.entity.Recipe

interface FavoriteRepository {
    fun addFavorite(item: Recipe)
    fun removeFavorite(item: Recipe)
    fun saveFavorite(favorites: List<Recipe>)
    fun getFavoriteRecipes(): List<Recipe>
}