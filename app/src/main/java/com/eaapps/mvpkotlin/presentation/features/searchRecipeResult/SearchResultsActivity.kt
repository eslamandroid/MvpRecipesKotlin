package com.eaapps.mvpkotlin.presentation.features.searchRecipeResult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.data.repository.FavoriteRepositoryImpl
import com.eaapps.mvpkotlin.databinding.ActivitySearchResultsBinding
import com.eaapps.mvpkotlin.domain.FavoriteUseCase
import com.eaapps.mvpkotlin.presentation.features.ChildActivity
import com.eaapps.mvpkotlin.presentation.features.showRecipe.recipeIntent


fun Context.searchResultsIntent(query: String): Intent {
    return Intent(this, SearchResultsActivity::class.java).apply {
        putExtra(EXTRA_QUERY, query)
    }
}

private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : ChildActivity(), SearchResultsPresenter.View {

    private lateinit var binding: ActivitySearchResultsBinding

    private lateinit var presenter: SearchResultsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SearchResultsPresenter(
            favoriteUseCase = FavoriteUseCase(
                FavoriteRepositoryImpl(
                    this.getSharedPreferences(
                        "EA",
                        MODE_PRIVATE
                    )
                )
            )
        )

        val query = intent.getStringExtra(EXTRA_QUERY)
        supportActionBar?.subtitle = query

        presenter.attachView(this)

        presenter.search(query!!)

    }


    private fun setupRecipeList(recipes: List<Recipe>) {
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onAddFavorite(item: Recipe) {
                presenter.addFavorite(item)
            }

            override fun onClickItem(item: Recipe) {
                startActivity(recipeIntent(item.source_url))
            }

            override fun onRemoveFavorite(item: Recipe) {
                presenter.removeFavorite(item)
            }
        })
    }

    override fun showLoading() {
        binding.loading.loadingContainer.visibility = View.VISIBLE
        binding.viewError.errorContainer.visibility = View.GONE
        binding.list.visibility = View.GONE
        binding.noResult.noresultsContainer.visibility = View.GONE

    }

    override fun showRecipes(recipes: List<Recipe>) {
        binding.loading.loadingContainer.visibility = View.GONE
        binding.viewError.errorContainer.visibility = View.GONE
        binding.list.visibility = View.VISIBLE
        binding.noResult.noresultsContainer.visibility = View.GONE
        setupRecipeList(recipes)
    }

    override fun showEmptyRecipes() {
        binding.loading.loadingContainer.visibility = View.GONE
        binding.viewError.errorContainer.visibility = View.GONE
        binding.list.visibility = View.VISIBLE
        binding.noResult.noresultsContainer.visibility = View.VISIBLE
    }

    override fun showError() {
        binding.loading.loadingContainer.visibility = View.GONE
        binding.viewError.errorContainer.visibility = View.VISIBLE
        binding.list.visibility = View.GONE
        binding.noResult.noresultsContainer.visibility = View.GONE
    }

    override fun refreshFavoriteStatus(recipesIndex: Int) {
        binding.list.adapter?.notifyItemChanged(recipesIndex)
    }
}