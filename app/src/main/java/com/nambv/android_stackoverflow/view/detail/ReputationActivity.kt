package com.nambv.android_stackoverflow.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.utils.addFragment
import com.nambv.android_stackoverflow.view.base.MainActivity

class ReputationActivity : MainActivity() {

    companion object {

        const val EXT_USER = "extra_user"

        fun getIntent(context: Context, user: User): Intent {
            val intent = Intent(context, ReputationActivity::class.java)
            intent.putExtra(EXT_USER, user)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar(true)

        val user= intent.getParcelableExtra<User>(EXT_USER)
        addFragment(supportFragmentManager, R.id.frameContainer, ReputationFragment.newInstance(user))
    }
}
