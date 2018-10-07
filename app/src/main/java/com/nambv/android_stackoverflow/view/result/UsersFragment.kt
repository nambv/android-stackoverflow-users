package com.nambv.android_stackoverflow.view.result

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.view.base.BaseFragment

class UsersFragment : BaseFragment() {

    private lateinit var viewModel: UsersViewModel

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun setUpView(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        viewModel.fetchUsers().observe(this, Observer {

            when (it) {

                is UsersState.Loading -> Timber.w { "Loading here" }
                is UsersState.Error -> {

                }
                is UsersState.Data -> {
                    Timber.w { "data here: ${it.users.size}" }
                }
            }
        })
    }
}