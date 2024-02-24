package com.example.foodorder

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryActivity : Menu() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderListAdapter: OrderListAdapter

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DBconnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderListAdapter = OrderListAdapter()
        recyclerView.adapter = orderListAdapter

        dbHelper = DBconnection(this)

        val orderList = getAllOrders()
        orderListAdapter.setData(orderList)
    }

    @SuppressLint("Range")
    private fun getAllOrders(): List<OrderListAdapter.Order> {
        val orderList = mutableListOf<OrderListAdapter.Order>()
        val db = dbHelper.readableDatabase

        val sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE)
        val Cid = sharedPreferences.getString("Cid", "")

        val cursor =
            db.rawQuery("SELECT * FROM orders WHERE Cid = ? ORDER BY Oid DESC", arrayOf(Cid))
        cursor.use {
            while (it.moveToNext()) {
                val foodId = it.getInt(it.getColumnIndex("Fid"))
                val quantity = it.getInt(it.getColumnIndex("Qty"))
                val totalAmount = it.getInt(it.getColumnIndex("Total_amount"))

                val foodName = getFoodNameById(foodId)
                val imageResId = getFoodImageResourceById(foodId)

                val order = OrderListAdapter.Order(foodName, quantity, totalAmount, imageResId)
                orderList.add(order)
            }
        }

        db.close()
        return orderList
    }

    @SuppressLint("Range")
    private fun getFoodImageResourceById(fid: Int): String {
        val db = dbHelper.readableDatabase
        var imageResource = ""

        val cursor = db.rawQuery("SELECT Image FROM food WHERE Fid = ?", arrayOf(fid.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                imageResource = it.getString(it.getColumnIndex("Image"))
            }
        }

        db.close()
        return imageResource
    }

    @SuppressLint("Range")
    private fun getFoodNameById(fid: Int): String {
        val db = dbHelper.readableDatabase
        var foodName = ""

        val cursor = db.rawQuery("SELECT Name FROM food WHERE Fid = ?", arrayOf(fid.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                foodName = it.getString(it.getColumnIndex("Name"))
            }
        }

        db.close()
        return foodName
    }
}
