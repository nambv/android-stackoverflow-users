package com.nambv.android_stackoverflow.view.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.data.Reputation
import com.nambv.android_stackoverflow.view.base.BaseListAdapter

class ReputationsAdapter(objects: MutableList<Reputation>) : BaseListAdapter<Reputation>(objects) {

    class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reputation, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: Reputation, position: Int) {

    }
}