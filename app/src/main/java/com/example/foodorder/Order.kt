package com.example.foodorder

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class Order : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var foodname: TextView
    private lateinit var price: TextView
    private lateinit var qty1: EditText
    private lateinit var totalprice: TextView
    private lateinit var btnorder: Button

    private lateinit var dbHelper: DBconnection
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        img = findViewById(R.id.img)
        foodname = findViewById(R.id.foodname)
        price = findViewById(R.id.price)
        qty1 = findViewById(R.id.qty1)
        totalprice = findViewById(R.id.totalprice)
        btnorder = findViewById(R.id.order)

        dbHelper = DBconnection(this)

        val food = intent.getSerializableExtra("food") as FoodAdapter.Food
        foodname.text = food.name
        price.text = "${food.price}"
        Picasso.get().load(food.image).into(img)
        calculateTotalAmount()
        qty1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                calculateTotalAmount()
            }
        })

        btnorder.setOnClickListener {
            val quantity = qty1.text.toString().toInt()

            if (quantity <= 0) {
                qty1.error = "Qty should be greater than 0"
                return@setOnClickListener
            }

            if (quantity >= 50) {
                qty1.error = "Qty should be less than 50"
                return@setOnClickListener
            }

            val totalAmount = quantity * food.price

            val orderId = insertOrder(food.fid, quantity, totalAmount)

            if (orderId != -1L) {
                Toast.makeText(this, "Order Success!", Toast.LENGTH_SHORT).show()
                val clicksignup = Intent(this@Order, OrderHistoryActivity::class.java)
                startActivity(clicksignup)
            } else {
                Toast.makeText(this, "Order failed!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun insertOrder(foodId: Int, quantity: Int, totalAmount: Int): Long {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues()

        val sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE)
        val Cid = sharedPreferences.getString("Cid", "")

        contentValues.put("Cid", Cid)
        contentValues.put("Fid", foodId)
        contentValues.put("Qty", quantity)
        contentValues.put("Total_amount", totalAmount)

        val result = db.insert("orders", null, contentValues)
        db.close()
        return result
    }


    private fun calculateTotalAmount() {
        val quantityStr = qty1.text.toString()
        val cakePriceStr = price.text.toString()

        if (quantityStr.isNotEmpty() && cakePriceStr.isNotEmpty()) {
            val quantity = quantityStr.toInt()
            val cakePrice = cakePriceStr.toInt()
            val amount = quantity * cakePrice
            totalprice.setText(amount.toString())
        }
    }
}