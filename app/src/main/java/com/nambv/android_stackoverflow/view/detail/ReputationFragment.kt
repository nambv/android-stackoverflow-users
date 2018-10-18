package com.nambv.android_stackoverflow.view.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import com.nambv.android_stackoverflow.data.User

class ReputationFragment : Fragment() {

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
}
