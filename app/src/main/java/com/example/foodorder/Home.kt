package com.example.foodorder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class Home : Menu() {

    private lateinit var dbHelper: DBconnection
    private lateinit var btn1: Button
    private lateinit var list: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn1 = findViewById(R.id.food)
        list = findViewById(R.id.cities)

        dbHelper = DBconnection(this)

        btn1.setOnClickListener(View.OnClickListener {

            val city2 = list.selectedItem.toString()
            val signUp = Intent(this@Home, FoodMenu::class.java)
            signUp.putExtra("city", city2)
            startActivity(signUp)
        })

        val city1 = getAllCity()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, city1)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        list.adapter = adapter

    }

    private fun getAllCity(): List<String> {
        val foodcity = mutableListOf<String>()

        val db = dbHelper.readableDatabase

        val projection = arrayOf("City")

        val cursor = db.query(
            "food",
            projection,
            null,
            null,
            "city",
            null,
            null
        )

        while (cursor.moveToNext()) {
            val city = cursor.getString(cursor.getColumnIndexOrThrow("City"))
            foodcity.add(city)
        }

        cursor.close()
        db.close()

        return foodcity
    }
}