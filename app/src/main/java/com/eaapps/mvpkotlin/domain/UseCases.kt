package com.eaapps.mvpkotlin.domain

import com.eaapps.mvpkotlin.data.repository.RecipeRepositoryImpl
import com.eaapps.mvpkotlin.domain.repository.FavoriteRepository
import com.eaapps.mvpkotlin.domain.repository.RecipeRepository

class RecipesUseCase(val recipeRepository: RecipeRepository = RecipeRepositoryImpl())

class FavoriteUseCase(val favoriteRepository: FavoriteRepository)
