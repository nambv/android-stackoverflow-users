package com.nambv.android_stackoverflow.view.result

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.utils.Constants.DATE_FORMAT
import com.nambv.android_stackoverflow.utils.getHtmlText
import com.nambv.android_stackoverflow.utils.toDate
import com.nambv.android_stackoverflow.utils.toString
import com.nambv.android_stackoverflow.view.base.BaseListAdapter
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(objects: MutableList<User>) : BaseListAdapter<User>(objects) {

    private lateinit var callback: Callback
    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return RecyclerViewHolder(v)
    }

    @Suppress("DEPRECATION")
    override fun onBindData(holder: RecyclerView.ViewHolder, item: User, position: Int) {

        Glide.with(holder.itemView.context)
                .load(item.profileImage)
                .into(holder.itemView.userAvatar)

        holder.itemView.tvUserName.text = item.displayName.getHtmlText()

        holder.itemView.tvReputation.text = item.reputation.toString()

        if (null == item.bookmarked) {
            holder.itemView.iconBookmark.setImageResource(R.drawable.ic_unbookmark)
        } else {
            if (item.bookmarked == true)
                holder.itemView.iconBookmark.setImageResource(R.drawable.ic_bookmark)
            else
                holder.itemView.iconBookmark.setImageResource(R.drawable.ic_unbookmark)
        }

        holder.itemView.tvLocation.text = item.location.getHtmlText()
        holder.itemView.tvLastAccess.text = (item.lastAccessDate * 1000).toDate().toString(DATE_FORMAT)

        holder.itemView.iconBookmark.setOnClickListener {
            if (null == item.bookmarked)
                item.bookmarked = true
            else
                item.bookmarked = !(item.bookmarked!!)

            Timber.w { "bookmarked: ${item.bookmarked}" }
            callback.onEditBookmark(item)
        }

        holder.itemView.setOnClickListener {

        }
    }

    interface Callback {
        fun onEditBookmark(user: User)
    }
}
