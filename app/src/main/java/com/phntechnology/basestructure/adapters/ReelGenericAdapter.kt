package com.phntechnology.basestructure.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


class ReelGenericAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val onBind: (T, VB,Int,Int,VB?) -> Unit,
    private val onRecycled: (VB) -> Unit = {},
    private val onDetached: () -> Unit = {}
) : RecyclerView.Adapter<ReelGenericAdapter<T, VB>.GenericViewHolder>() {

    private var previousViewHolder: VB? = null

    private var data = ArrayList<T>()

    private lateinit var context: Context

    inner class GenericViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        context = parent.context
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        onBind(data[position], holder.binding,position,data.size,previousViewHolder)
        previousViewHolder = holder.binding
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onDetached()
    }

    override fun onViewRecycled(holder: GenericViewHolder) {
        super.onViewRecycled(holder)
        onRecycled(holder.binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(newData: ArrayList<T>) {
        val genericDiffUtil = GenericDiffUtil(data, newData)
        val genericDiff = DiffUtil.calculateDiff(genericDiffUtil)
        data = newData
        genericDiff.dispatchUpdatesTo(this)
    }

    inner class GenericDiffUtil(
        private val oldData: ArrayList<T>,
        private val newData: ArrayList<T>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldData.size

        override fun getNewListSize() = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition] == newData[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition].toString().contentEquals(newData[newItemPosition].toString())
    }
}