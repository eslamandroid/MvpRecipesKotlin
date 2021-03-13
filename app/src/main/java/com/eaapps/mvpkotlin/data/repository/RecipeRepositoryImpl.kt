package com.eaapps.mvpkotlin.data.repository

import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.data.entity.RecipeEntities
import com.eaapps.mvpkotlin.data.entity.RecipeItem
import com.eaapps.mvpkotlin.data.service.RecipeServiceApi
import com.eaapps.mvpkotlin.domain.repository.RecipeRepository
import com.eaapps.mvpkotlin.domain.repository.RepositoryCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeRepositoryImpl(
    private val recipesApi: Lazy<RecipeServiceApi> = lazy {
        RecipeServiceApi.create()
    }
) : RecipeRepository {

    override fun searchRecipes(q: String, callback: RepositoryCallback<List<Recipe>>) {
        recipesApi.value.search(q).enqueue(object : Callback<RecipeEntities> {
            override fun onFailure(call: Call<RecipeEntities>, t: Throwable) {
                callback.onError()


            }

            override fun onResponse(
                call: Call<RecipeEntities>?,
                response: Response<RecipeEntities>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val recipeEntities = response.body();
                    callback.onSuccess(recipeEntities?.recipes)
                } else
                    callback.onError()

            }
        })
    }

    override fun getRecipe(recipeId: Int, callback: RepositoryCallback<Recipe>) {
        recipesApi.value.recipeItem(recipeId).enqueue(object : Callback<RecipeItem> {
            override fun onResponse(call: Call<RecipeItem>, response: Response<RecipeItem>) {
                if (response.isSuccessful && response.body() != null) {
                    val recipeItem = response.body();
                    callback.onSuccess(recipeItem?.recipe)
                } else
                    callback.onError()
            }

            override fun onFailure(call: Call<RecipeItem>, t: Throwable) {
                callback.onError()
            }

        })
    }
}
