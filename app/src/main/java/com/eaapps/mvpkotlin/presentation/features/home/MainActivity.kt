package com.eaapps.mvpkotlin.presentation.features.home

import android.content.Intent
import android.os.Bundle
import com.eaapps.mvpkotlin.databinding.ActivityMainBinding
import com.eaapps.mvpkotlin.presentation.features.favorite.FavoritesActivity
import com.eaapps.mvpkotlin.presentation.features.searchRecipe.SearchActivity
import com.eaapps.mvpkotlin.presentation.features.ChildActivity

class MainActivity : ChildActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.favButton.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }
}