package com.nambv.android_stackoverflow.view.result

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.utils.Constants.PAGE_SIZE
import com.nambv.android_stackoverflow.utils.EndlessRecyclerOnScrollListener
import com.nambv.android_stackoverflow.utils.VerticalSpaceItemDecoration
import com.nambv.android_stackoverflow.utils.getErrorMessage
import com.nambv.android_stackoverflow.utils.getSelectedBookmarked
import com.nambv.android_stackoverflow.view.base.BaseRecyclerViewFragment
import com.nambv.android_stackoverflow.view.detail.ReputationActivity
import kotlinx.android.synthetic.main.fragment_base_list.*


class UsersFragment : BaseRecyclerViewFragment(), SwipeRefreshLayout.OnRefreshListener, UsersAdapter.Callback {

    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var scrollListener: EndlessRecyclerOnScrollListener

    private var users = mutableListOf<User>()
    private var page: Int = 1
    private var filterPosition = 0

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filterAction -> showFilterDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setupView() {
        super.setupView()
        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        setupRecyclerView()
        fetchUsers()
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val divider = VerticalSpaceItemDecoration(30)

        scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentOffset: Int) { fetchUsers() }
        }

        adapter = UsersAdapter(users)
        adapter.setCallback(this)

        setupRecyclerView(layoutManager, adapter, divider, scrollListener)
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

        val bookmarked = getSelectedBookmarked(filterPosition)

        viewModel.fetchUsers(page, PAGE_SIZE, bookmarked).observe(this, Observer {

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

        if (this.page == 1 || getSelectedBookmarked(filterPosition) != null) {
            if (this.page == 1) this.page++
            this.users.clear()
        }

        this.users.addAll(users)
        recyclerView.post { this.adapter.notifyDataSetChanged() }
    }

    override fun onRefresh() {

        // Reset pagination
        page = 1
        scrollListener.reset(0, false)

        fetchUsers()
    }

    private fun showFilterDialog() {
        AlertDialog.Builder(context)
                .setTitle(R.string.label_filter_users)
                .setSingleChoiceItems(R.array.filters, filterPosition, null)
                .setPositiveButton(R.string.label_ok) { dialog, _ ->
                    dialog.dismiss()
                    filterPosition = (dialog as AlertDialog).listView.checkedItemPosition
                    onRefresh()
                }
                .setNegativeButton(R.string.label_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    override fun onUserClicked(user: User) {
        startActivity(ReputationActivity.getIntent(context, user))
    }

    override fun onEditBookmark(user: User) {
        viewModel.updateUser(user).observe(this, Observer {
            when (it) {

                is UsersState.Updated -> {
                    val bookmarked = getSelectedBookmarked(filterPosition)
                    bookmarked?.let { _ -> users.remove(user) }
                    adapter.notifyDataSetChanged()
                }

                is UsersState.Error -> showToast(context.getErrorMessage(it.throwable))
            }
        })
    }
}