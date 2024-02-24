package com.example.foodorder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.Serializable

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    private var orderList: List<Order> = listOf()

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.foodname)
        val quantityTextView: TextView = itemView.findViewById(R.id.qty)
        val totalAmountTextView: TextView = itemView.findViewById(R.id.totalprice)
        val foodImageView: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.orders, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.foodNameTextView.text = order.foodName
        holder.quantityTextView.text = "${order.quantity}"
        holder.totalAmountTextView.text = "${order.totalAmount}"

        Picasso.get().load(order.imageResId).into(holder.foodImageView)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(orders: List<Order>) {
        orderList = orders
        notifyDataSetChanged()
    }

    data class Order(
        val foodName: String,
        val quantity: Int,
        val totalAmount: Int,
        val imageResId: String
    ) : Serializable
}
