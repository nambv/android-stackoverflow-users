package com.nambv.android_stackoverflow.view.result

import android.os.Bundle
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.view.base.BaseActivity

/**
 * Weather Result View
 */
class UsersActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction()
                .add(R.id.frameContainer, UsersFragment.newInstance())
                .commit()
    }
}