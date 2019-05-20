package com.aleksandr.aleksandrov.weatherfornatife.base_classes

import android.support.v7.widget.RecyclerView

/**
 * Created by Alexandrov Alex on 2019-05-20.
 */
abstract class BaseRecyclerViewAdapter<E, VH : RecyclerView.ViewHolder>
    : RecyclerView.Adapter<VH>() {

    protected var data: MutableList<E> = ArrayList()

    override fun getItemCount(): Int {
        return data.size
    }

    open fun setAdapterData(data: MutableList<E>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getAdapterData(): MutableList<E> {
        return data
    }

    fun updateItem(item: E) {
        val index = data.indexOf(item)
        data[index] = item
        notifyItemChanged(index)
    }
}