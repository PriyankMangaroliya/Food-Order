package com.example.foodorder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.Serializable


class FoodAdapter(private val context: Context, private val foodList: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menuitem, parent, false)
        return FoodViewHolder(view)
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.foodname)
        val priceTextView: TextView = itemView.findViewById(R.id.price)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val orderButton: Button = itemView.findViewById(R.id.btn)
        val imageView: ImageView = itemView.findViewById(R.id.img)

        init {
            orderButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val food = foodList[position]
                    val intent = Intent(context, Order::class.java).apply {
                        putExtra("food", food)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.nameTextView.text = food.name
        holder.priceTextView.text = "${food.price}"
        holder.descriptionTextView.text = food.description

        Picasso.get().load(food.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    data class Food(
        val fid: Int,
        val name: String,
        val price: Int,
        val image: String,
        val description: String,
        val city: String
    ) : Serializable
}