package com.nambv.android_stackoverflow.view.detail


import android.os.Bundle
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.view.base.BaseRecyclerViewFragment

class ReputationFragment : BaseRecyclerViewFragment() {

    companion object {

        const val ARG_USER = "argument_user"

        fun newInstance(user: User): ReputationFragment {

            val arg = Bundle()
            arg.putParcelable(ARG_USER, user)

            val detailFragment = ReputationFragment()
            detailFragment.arguments = arg
            return detailFragment
        }
    }

    override fun setupView() {
    }

    override fun onRefresh() {

    }
}
