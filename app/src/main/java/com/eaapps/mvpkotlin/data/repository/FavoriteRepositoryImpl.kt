package com.eaapps.mvpkotlin.data.repository

import android.content.SharedPreferences
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.domain.repository.FavoriteRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val FAVORITES_KEY = "FAVORITES_KEY"

class FavoriteRepositoryImpl(private val sharedPreferences: SharedPreferences) : FavoriteRepository {

    private val json = Gson()

    override fun addFavorite(item: Recipe) = saveFavorite(getFavoriteRecipes() + item)

    override fun removeFavorite(item: Recipe) = saveFavorite(getFavoriteRecipes() - item)

    @Suppress("UNREACHABLE_CODE")
    override fun saveFavorite(favorites: List<Recipe>) {
        @Suppress("UNREACHABLE_CODE") val editor = sharedPreferences.edit()
        editor.putString(FAVORITES_KEY, json.toJson(favorites))
        editor.apply()
    }

    override fun getFavoriteRecipes(): List<Recipe> {
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, null)
        return if (favoritesString != null)
            json.fromJson(favoritesString)
        else
            emptyList()
    }

    private inline fun <reified T> Gson.fromJson(json: String?): T =
        this.fromJson(json, object : TypeToken<T>() {}.type)

}