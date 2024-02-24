package com.example.foodorder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btn1: Button
    lateinit var btn2: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE)
        if (sharedPreferences.contains("Contact") && sharedPreferences.contains("Password")) {
            val a = Intent(this@MainActivity, Home::class.java)
            startActivity(a)
            finish()
        }

        btn1 = findViewById(R.id.login)
        btn2 = findViewById(R.id.register)

        btn1.setOnClickListener(View.OnClickListener {
            val signUp = Intent(this@MainActivity, Login::class.java)
            startActivity(signUp)
        })

        btn2.setOnClickListener(View.OnClickListener {
            val signIn = Intent(this@MainActivity, Registration::class.java)
            startActivity(signIn)
        })
    }
}