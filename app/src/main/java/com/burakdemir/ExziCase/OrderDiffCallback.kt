package com.burakdemir.ExziCase

import androidx.recyclerview.widget.DiffUtil
import com.burakdemir.ExziCase.model.OrderBookEntry

class OrderDiffCallback(private val oldList: List<OrderBookEntry>, private val newList: List<OrderBookEntry>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uniqueId == newList[newItemPosition].uniqueId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
