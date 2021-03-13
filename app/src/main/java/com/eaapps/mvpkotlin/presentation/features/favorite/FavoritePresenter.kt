package com.eaapps.mvpkotlin.presentation.features.favorite

import android.util.Log
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.domain.FavoriteUseCase
import com.eaapps.mvpkotlin.presentation.features.BasePresenter
import kotlin.math.log

class FavoritePresenter(private val favoriteUseCase: FavoriteUseCase) :
    BasePresenter<FavoritePresenter.View>() {
    private var recipes: List<Recipe> = emptyList()

     fun getRecipes() {
        view?.showLoading()
        val favoriteRecipe = favoriteUseCase.favoriteRepository.getFavoriteRecipes()
        this@FavoritePresenter.recipes = favoriteRecipe

        if (favoriteRecipe.isNotEmpty()) {
            view?.showRecipes(favoriteRecipe)
        } else {
            view?.showEmptyRecipes()
        }
    }

    fun removeFavorite(recipe: Recipe) {
        favoriteUseCase.favoriteRepository.removeFavorite(recipe)
        refreshFavoriteStatue(recipe)
    }

    private fun refreshFavoriteStatue(recipe: Recipe) {
        if (recipes.isNotEmpty()) {
            val recipeIndex = recipes.indexOf(recipe)
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }

    interface View {
        fun showRecipes(recipes: List<Recipe>)
        fun showLoading()
        fun showEmptyRecipes()
        fun refreshFavoriteStatus(recipeIndex: Int)
    }
}