package com.eaapps.mvpkotlin.domain.repository

import com.eaapps.mvpkotlin.data.entity.Recipe

interface RecipeRepository {

    fun searchRecipes(q: String, callback: RepositoryCallback<List<Recipe>>)

    fun getRecipe(recipeId: Int, callback: RepositoryCallback<Recipe>)
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: T?)
    fun onError()
}