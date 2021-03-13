package com.eaapps.mvpkotlin

import android.content.SharedPreferences
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.data.repository.FavoriteRepositoryImpl
import com.eaapps.mvpkotlin.domain.FavoriteUseCase
import com.eaapps.mvpkotlin.domain.RecipesUseCase
import com.eaapps.mvpkotlin.domain.repository.FavoriteRepository
import com.eaapps.mvpkotlin.domain.repository.RecipeRepository
import com.eaapps.mvpkotlin.domain.repository.RepositoryCallback
import com.eaapps.mvpkotlin.presentation.features.searchRecipeResult.SearchResultsPresenter
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchResultTests {

    private lateinit var recipeRepository: RecipeRepository
    private lateinit var recipesUseCase: RecipesUseCase
    private lateinit var presenter: SearchResultsPresenter
    private lateinit var view: SearchResultsPresenter.View
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var favoriteUseCase: FavoriteUseCase
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        recipeRepository = mock()
        recipesUseCase = RecipesUseCase(recipeRepository)
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        favoriteRepository = spy(FavoriteRepositoryImpl(sharedPreferences))
        favoriteUseCase = FavoriteUseCase(favoriteRepository)


        view = mock()
        presenter = SearchResultsPresenter(recipesUseCase, favoriteUseCase)
        presenter.attachView(view)
    }

    @Test
    fun `search verify call ShowLoading`() {
        presenter.search("chicken")
        verify(view).showLoading()
    }

    @Test
    fun `search verify call search searchRecipes()`() {
        presenter.search("chicken")

        /*
        eq: matcher is used to verify that it's called with the same string
        any: matcher was used for the callback parameter because you don't care about it
         */
        verify(recipeRepository).searchRecipes(eq("chicken"), any())
    }

    @Test
    fun `search verify 'with repository having recipes' call ShowRecipes`() {
        val recipe = Recipe(
            recipe_id = "id",
            title = "title",
            image_url = "imageUrl",
            source_url = "sourceUrl",
            favorited = false
        )
        val recipes = listOf(recipe)

        doAnswer {
            val callback: RepositoryCallback<List<Recipe>> = it.getArgument(1)
            callback.onSuccess(recipes)
        }.whenever(recipeRepository).searchRecipes(eq("chicken"), any())

        presenter.search("chicken")
        verify(view).showRecipes(eq(recipes))
    }

    @Test
    fun `add favorite should update recipe status`() {
        val recipe = Recipe(
            recipe_id = "id",
            title = "title",
            image_url = "imageUrl",
            source_url = "sourceUrl",
            favorited = false
        )

        presenter.addFavorite(recipe)

        Assert.assertTrue(recipe.favorited)


    }

    @Test
    fun `add favorite with empty recipes save json recipe`(){
        doReturn(emptyList<Recipe>()).whenever(favoriteRepository).getFavoriteRecipes()

        val recipe = Recipe(
            recipe_id = "id",
            title = "title",
            image_url = "imageUrl",
            source_url = "sourceUrl",
            favorited = false
        )

        /*
        1- Call the real addFavorite method with a recipe
        2- Check that the subsequent verifications are executed in the exact order
        3- Verify that the list is saved correctly with JSON format
         */

        favoriteRepository.addFavorite(recipe)

        inOrder(sharedPreferencesEditor){
            val  jsonString = Gson().toJson(listOf(recipe))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()

        }

    }
}