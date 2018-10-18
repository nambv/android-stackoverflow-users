package com.nambv.android_stackoverflow.view.result

import android.os.Bundle
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.utils.addFragment
import com.nambv.android_stackoverflow.view.base.MainActivity

/**
 * Weather Result View
 */
class UsersActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar(false)
        addFragment(supportFragmentManager, R.layout.activity_main, UsersFragment.newInstance())
    }
}