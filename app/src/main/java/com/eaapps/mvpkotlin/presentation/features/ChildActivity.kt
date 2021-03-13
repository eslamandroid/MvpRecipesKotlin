package com.eaapps.mvpkotlin.presentation.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class ChildActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}