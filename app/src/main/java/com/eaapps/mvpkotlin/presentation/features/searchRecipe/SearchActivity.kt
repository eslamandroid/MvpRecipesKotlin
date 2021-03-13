package com.eaapps.mvpkotlin.presentation.features.searchRecipe

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.eaapps.mvpkotlin.R
import com.eaapps.mvpkotlin.databinding.ActivitySearchBinding
import com.eaapps.mvpkotlin.presentation.features.ChildActivity
import com.eaapps.mvpkotlin.presentation.features.searchRecipeResult.searchResultsIntent
import com.google.android.material.snackbar.Snackbar

class SearchActivity : ChildActivity(), SearchPresenter.View {
    private val presenter: SearchPresenter = SearchPresenter()
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        binding.searchButton.setOnClickListener {
            presenter.search(binding.ingredients.text.toString())
        }
    }

    override fun showQueryRequiredMessage() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Snackbar.make(
            binding.searchButton,
            getString(R.string.search_query_required),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun showSearchResults(query: String) {
        startActivity(searchResultsIntent(query))
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}