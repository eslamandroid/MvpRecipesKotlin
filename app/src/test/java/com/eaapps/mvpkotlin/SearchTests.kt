package com.eaapps.mvpkotlin

import com.eaapps.mvpkotlin.presentation.features.searchRecipe.SearchPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class SearchTests {

    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchPresenter.View

    @Before
    fun setup() {
        presenter = SearchPresenter()
        view = mock() // Because of you don't need a real view that conforms to the interface,you can mock it
        presenter.attachView(view) // attach this view to the presenter
    }

    @Test
    fun `Case 1- search with empty query and verify call ShowQueryRequiredMessage()`() {
        presenter.search("") // call the search method with an empty query
        verify(view).showQueryRequiredMessage() // verify on the mocked view that the presenter calls showQueryRequiredMessage()
    }

    @Test
    fun `Case 2- search with empty query does not call ShowQueryRequiredMessage()`(){
        presenter.search("")
        verify(view, never()).showQueryRequiredMessage()

    }


}