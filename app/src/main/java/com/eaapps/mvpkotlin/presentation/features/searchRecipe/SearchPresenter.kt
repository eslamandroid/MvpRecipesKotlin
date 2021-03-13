package com.eaapps.mvpkotlin.presentation.features.searchRecipe

import com.eaapps.mvpkotlin.presentation.features.BasePresenter

class SearchPresenter : BasePresenter<SearchPresenter.View>() {

    fun search(query: String) {
        if (query.trim().isBlank())
            view?.showQueryRequiredMessage()
        else
            view?.showSearchResults(query)
    }


    interface View {
        fun showQueryRequiredMessage()
        fun showSearchResults(query: String)
    }

}