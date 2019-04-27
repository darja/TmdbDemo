package com.darja.tmdb.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.darja.tmdb.BR

abstract class DataBindingRecyclerAdapter<DATA>(
        var items: ObservableArrayList<DATA>,
        var onClickListener: ((data: DATA, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BindingViewHolder>() {

    init {
        items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<DATA>>() {
            override fun onChanged(sender: ObservableList<DATA>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<DATA>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<DATA>, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeMoved(sender: ObservableList<DATA>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeChanged(sender: ObservableList<DATA>, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        val holder = BindingViewHolder(binding)
        binding.lifecycleOwner = holder

        return holder
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getViewModel(position)
                ?.let {
                    val bindingSuccess = holder.binding.setVariable(BR.viewModel, it)
                    holder.binding.setVariable(BR.onClickListener, onClickListener.toInternalOnClickListener(items[position], position))
                    if (!bindingSuccess) {
                        throw IllegalStateException("Binding ${holder.binding} vm variable name should be 'vm'")
                    }
                }
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    open fun getViewModel(position: Int): Any? = items[position]

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

    abstract fun getLayoutIdForPosition(position: Int): Int

    fun ((data: DATA, position: Int) -> Unit)?.toInternalOnClickListener(data: DATA, position: Int): View.OnClickListener {
        return View.OnClickListener { this@toInternalOnClickListener?.invoke(data, position) }
    }
}