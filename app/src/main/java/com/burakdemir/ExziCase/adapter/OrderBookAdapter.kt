package com.burakdemir.ExziCase.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.burakdemir.ExziCase.OrderDiffCallback
import com.burakdemir.ExziCase.R
import com.burakdemir.ExziCase.model.OrderBookEntry

class OrderBookAdapter(private var orders: MutableList<OrderBookEntry>, private var orderType: String) : RecyclerView.Adapter<OrderBookAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val amountTextView: TextView = view.findViewById(R.id.tvAmount)
        private val priceTextView: TextView = view.findViewById(R.id.tvPrice)
        private val orderBookProgressBar: ProgressBar = view.findViewById(R.id.orderBookProgressBar)
        fun bind(order: OrderBookEntry, orderType: String, totalVolume: Double) {
            val amount = order.volume_f.toDouble()/order.rate_f.toDouble()
            val formattedResult = String.format("%.2f", amount*100000)
            amountTextView.text = formattedResult
            priceTextView.text = "${order.rate_f}"
            orderBookProgressBar.progress = (order.volume*100 / totalVolume).toInt()
            if (orderType == "buy"){
                orderBookProgressBar.progressTintList = ColorStateList.valueOf(Color.RED);
            }
            else{
                orderBookProgressBar.progressTintList = ColorStateList.valueOf(Color.GREEN)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_book_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        var totalVolume = 0.0
        orders.map { totalVolume += it.volume.toDouble() }
        holder.bind(orders[position], this.orderType, totalVolume )
    }

    override fun getItemCount() = orders.size

    fun updateOrders(newOrders: MutableList<OrderBookEntry>) {
        val diffResult = DiffUtil.calculateDiff(OrderDiffCallback(orders, newOrders))
        orders.clear()
        orders.addAll(newOrders)
        diffResult.dispatchUpdatesTo(this)
    }
}
