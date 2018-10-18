package com.nambv.android_stackoverflow.view.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_base_list.*


abstract class BaseRecyclerViewFragment<T : BaseViewModel> : BaseFragment<T>(), SwipeRefreshLayout.OnRefreshListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_list, container, false)
    }

    override fun setupView() {
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    fun setupRecyclerView(layoutManager: RecyclerView.LayoutManager,
                          adapter: RecyclerView.Adapter<*>,
                          itemDecorator: RecyclerView.ItemDecoration?,
                          scrollListener: EndlessRecyclerOnScrollListener?) {

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(itemDecorator)
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    abstract override fun onRefresh()
}