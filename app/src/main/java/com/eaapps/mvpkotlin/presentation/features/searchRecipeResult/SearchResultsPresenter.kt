package com.eaapps.mvpkotlin.presentation.features.searchRecipeResult

import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.domain.FavoriteUseCase
import com.eaapps.mvpkotlin.domain.RecipesUseCase
import com.eaapps.mvpkotlin.domain.repository.RepositoryCallback
import com.eaapps.mvpkotlin.presentation.features.BasePresenter

class SearchResultsPresenter(
    private val recipesUseCase: RecipesUseCase = RecipesUseCase(),
    private val favoriteUseCase: FavoriteUseCase
) :
    BasePresenter<SearchResultsPresenter.View>() {

    private var recipes: List<Recipe>? = null

    fun search(query: String) {
        view?.showLoading()
        recipesUseCase.recipeRepository.searchRecipes(query,
            object : RepositoryCallback<List<Recipe>> {
                override fun onError() {
                    view?.showError()
                }

                override fun onSuccess(t: List<Recipe>?) {
                    this@SearchResultsPresenter.recipes = t
                    if (t != null && t.isNotEmpty()) {
                        val favoriteRecipe = favoriteUseCase.favoriteRepository.getFavoriteRecipes()
                        if (favoriteRecipe.isNotEmpty())
                            for (item in t)
                                item.favorited =
                                    favoriteRecipe.map { it.recipe_id }.contains(item.recipe_id)
                        view?.showRecipes(t)
                    }
                }
            })
    }

    fun addFavorite(recipe: Recipe) {
        recipe.favorited = true
        favoriteUseCase.favoriteRepository.addFavorite(recipe)
        refreshFavoriteStatue(recipe)
    }

    fun removeFavorite(recipe: Recipe) {
        favoriteUseCase.favoriteRepository.removeFavorite(recipe)
        recipe.favorited = true
        refreshFavoriteStatue(recipe)

    }

    private fun refreshFavoriteStatue(recipe: Recipe) {
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null)
            view?.refreshFavoriteStatus(recipeIndex)
    }


    interface View {
        fun showLoading()
        fun showRecipes(recipes: List<Recipe>)
        fun showEmptyRecipes()
        fun showError()
        fun refreshFavoriteStatus(recipesIndex: Int)
    }
}