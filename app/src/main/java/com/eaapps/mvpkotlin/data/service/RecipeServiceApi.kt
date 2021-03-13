package com.eaapps.mvpkotlin.data.service

import com.eaapps.mvpkotlin.data.entity.RecipeEntities
import com.eaapps.mvpkotlin.data.entity.RecipeItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeServiceApi {

    @GET("search")
    fun search(@Query("q") query: String): Call<RecipeEntities>

    @GET("get")
    fun recipeItem(@Query("rId") recipeId: Int): Call<RecipeItem>

    companion object Factory {
        fun create(): RecipeServiceApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://recipesapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RecipeServiceApi::class.java)
        }
    }


}