package com.eaapps.mvpkotlin.presentation.features.showRecipe

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import com.eaapps.mvpkotlin.databinding.ActivityRecipeBinding
import com.eaapps.mvpkotlin.presentation.features.ChildActivity

fun Context.recipeIntent(url: String): Intent {
    return Intent(this, RecipeActivity::class.java).apply {
        putExtra(EXTRA_URL, url)
    }
}

private const val EXTRA_URL = "EXTRA_URL"

class RecipeActivity : ChildActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRecipeBinding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_URL)

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        binding.webView.isHorizontalScrollBarEnabled = false
        binding.webView.loadUrl(url!!.replace("http","https"))
        binding.webView.webViewClient = object : WebViewClient() {}


    }
}