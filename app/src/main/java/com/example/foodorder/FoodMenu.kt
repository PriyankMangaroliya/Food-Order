package com.example.foodorder

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodMenu : Menu() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBconnection
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_menu)

        val city123 = intent.getStringExtra("city")

        recyclerView = findViewById(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = DBconnection(this)

        val foodList = fetchDataFromDatabase(city123)

        foodAdapter = FoodAdapter(this, foodList)
        recyclerView.adapter = foodAdapter
    }

    private fun fetchDataFromDatabase(city: String?): List<FoodAdapter.Food> {
        val foodList = mutableListOf<FoodAdapter.Food>()

        val db = dbHelper.readableDatabase

        val projection = arrayOf("Fid", "Name", "Price", "Image", "Description", "City")
        val selection = "City = ?"
        val selectionArgs = arrayOf(city)

        val cursor = db.query(
            "food",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val fid = cursor.getInt(cursor.getColumnIndexOrThrow("Fid"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow("Price"))
            val image = cursor.getString(cursor.getColumnIndexOrThrow("Image"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("Description"))
            val city = cursor.getString(cursor.getColumnIndexOrThrow("City"))

            val food = FoodAdapter.Food(fid, name, price, image, description, city)
            foodList.add(food)
        }

        cursor.close()
        db.close()

        return foodList
    }
}