package com.nambv.android_stackoverflow.view.result

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.utils.Constants.DATE_FORMAT
import com.nambv.android_stackoverflow.utils.toDate
import com.nambv.android_stackoverflow.utils.toString
import com.nambv.android_stackoverflow.view.base.BaseListAdapter
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(objects: MutableList<User>) : BaseListAdapter<User>(objects) {

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

        holder.itemView.tvUserName.text = item.displayName
        holder.itemView.tvReputation.text = item.reputation.toString()

        holder.itemView.tvLocation.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.location, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(item.location)
                }

        holder.itemView.tvLastAccess.text = (item.lastAccessDate * 1000).toDate().toString(DATE_FORMAT)

        holder.itemView.iconBookmark.setOnClickListener {

        }

        holder.itemView.setOnClickListener {

        }
    }
}
