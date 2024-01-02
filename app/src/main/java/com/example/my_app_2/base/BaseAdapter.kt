package com.example.my_app_2.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : ViewDataBinding> : RecyclerView.Adapter<BaseAdapter<T>.BindingViewHolder>() {

    abstract fun onBindItem(binding: ViewDataBinding, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return getViewHolder(viewType, parent)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        onBindItem(holder.binding, holder.adapterPosition)
    }

    inner class BindingViewHolder(val binding: T) : RecyclerView.ViewHolder(binding.root)

    private fun getViewHolder(id: Int, parent: ViewGroup): BindingViewHolder {
        return when (id) {

//            // Just to avoid crash
//            0 -> BindingViewHolder(
//                DataBindingUtil.inflate(
//                    LayoutInflater.from(parent.context),
//                    R.layout.item_resident_list,
//                    parent,
//                    false
//                ) as T
//            )
            else -> BindingViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    id,
                    parent,
                    false
                ) as T
            )
        }
    }
}
