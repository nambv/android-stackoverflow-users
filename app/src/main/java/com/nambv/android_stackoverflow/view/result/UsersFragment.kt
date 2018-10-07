package com.nambv.android_stackoverflow.view.result

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.utils.Constants.PAGE_SIZE
import com.nambv.android_stackoverflow.utils.EndlessRecyclerOnScrollListener
import com.nambv.android_stackoverflow.utils.getErrorMessage
import com.nambv.android_stackoverflow.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_users.*


class UsersFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var scrollListener: EndlessRecyclerOnScrollListener

    private var users = mutableListOf<User>()
    private var page: Int = 1

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun setUpView(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        fetchUsers()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        adapter = UsersAdapter(users)
        scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentOffset: Int) {
                fetchUsers()
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(divider)
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.adapter = adapter
    }

    private fun showRefreshing() {
        swipeRefreshLayout.isRefreshing = true
    }

    private fun showLoadMore() {
        loadMoreView.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        if (page > 1)
            loadMoreView.visibility = View.GONE
        else
            swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchUsers() {

        Timber.w { "fetchUsers: $page" }

        viewModel.fetchUsers(page, PAGE_SIZE).observe(this, Observer {

            when (it) {

                is UsersState.Refreshing -> showRefreshing()

                is UsersState.LoadMore -> showLoadMore()

                is UsersState.Error -> {
                    hideLoadingView()
                    showToast(context.getErrorMessage(it.throwable))
                }
                is UsersState.Data -> {
                    hideLoadingView()
                    onUsersReceived(it.users)
                }
            }
        })
    }

    private fun onUsersReceived(users: List<User>) {

        if (this.page == 1) this.users.clear()

        this.page++
        this.users.addAll(users)
        recyclerView.post { this.adapter.notifyDataSetChanged() }
    }

    override fun onRefresh() {

        // Reset pagination
        page = 1
        scrollListener.reset(0, false)

        fetchUsers()
    }
}