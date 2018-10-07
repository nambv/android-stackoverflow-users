package com.nambv.android_stackoverflow.view.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


/**
 * Used to extended list adapter for RecyclerView
 */
abstract class BaseListAdapter<T>(protected var context: Context?, objects: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var objects: MutableList<T> = mutableListOf()

    abstract fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, item: T, position: Int)

    init {
        this.objects = objects
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, objects[position], position)
    }

    override fun getItemCount(): Int = objects.size

    fun removeItem(position: Int) {
        objects.removeAt(position)
        notifyDataSetChanged()
    }
}