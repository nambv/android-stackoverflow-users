package com.nambv.android_stackoverflow.view.base

import android.os.Bundle
import com.nambv.android_stackoverflow.R

abstract class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setupActionBar(displayHome: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHome)
    }
}
