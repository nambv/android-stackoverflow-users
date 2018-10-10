package com.nambv.android_stackoverflow.view.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.view.base.BaseRecyclerViewFragment

class DetailFragment : BaseRecyclerViewFragment() {

    companion object {

        const val ARG_USER = "argument_user"

        fun newInstance(user: User): DetailFragment {

            val arg = Bundle()
            arg.putParcelable(ARG_USER, user)

            val detailFragment = DetailFragment()
            detailFragment.arguments = arg
            return detailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun setupView() {
    }

    override fun onRefresh() {

    }
}
