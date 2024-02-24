package com.example.foodorder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class Menu : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Home -> {
                val click = Intent(this, Home::class.java)
                startActivity(click)
                return true
            }

            R.id.Order -> {
                val orders = Intent(this, OrderHistoryActivity::class.java)
                startActivity(orders)
                return true
            }

            R.id.Logout -> {
                if (!::sharedPreferences.isInitialized) {
                    sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
                }
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}