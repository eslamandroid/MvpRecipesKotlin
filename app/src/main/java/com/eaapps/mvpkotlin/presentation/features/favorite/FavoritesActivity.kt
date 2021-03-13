package com.eaapps.mvpkotlin.presentation.features.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.data.repository.FavoriteRepositoryImpl
import com.eaapps.mvpkotlin.databinding.ActivityFavoritesBinding
import com.eaapps.mvpkotlin.domain.FavoriteUseCase
import com.eaapps.mvpkotlin.presentation.features.ChildActivity
import com.eaapps.mvpkotlin.presentation.features.showRecipe.recipeIntent


class FavoritesActivity : ChildActivity(), FavoritePresenter.View {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var presenter: FavoritePresenter
    private lateinit var adapter: FavoriteRecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = FavoritePresenter(
            favoriteUseCase = FavoriteUseCase(
                FavoriteRepositoryImpl(
                    this.getSharedPreferences(
                        "EA",
                        MODE_PRIVATE
                    )
                )
            )
        )
        presenter.attachView(this)
        presenter.getRecipes()
    }

    private fun setupList(recipes: List<Recipe>) {
        adapter = FavoriteRecipeAdapter(
            recipes as MutableList<Recipe>,
            object : FavoriteRecipeAdapter.Listener {
                override fun onClickItem(item: Recipe) {
                    startActivity(recipeIntent(item.source_url))
                }

                override fun onRemoveFavorite(item: Recipe) {
                    presenter.removeFavorite(item)
                }

            })
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
    }

    override fun refreshFavoriteStatus(recipeIndex: Int) {
        adapter.remove(recipeIndex)
        if ((binding.list.adapter?.itemCount)!! <= 0)
            showEmptyRecipes()
    }

    override fun showRecipes(recipes: List<Recipe>) {
        binding.list.visibility = View.VISIBLE
        binding.noResult.noresultsContainer.visibility = View.GONE
        binding.loading.loadingContainer.visibility = View.GONE
        setupList(recipes)
    }

    override fun showLoading() {
        binding.list.visibility = View.GONE
        binding.noResult.noresultsContainer.visibility = View.GONE
        binding.loading.loadingContainer.visibility = View.VISIBLE
    }

    override fun showEmptyRecipes() {
        binding.list.visibility = View.GONE
        binding.noResult.noresultsContainer.visibility = View.VISIBLE
        binding.loading.loadingContainer.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}