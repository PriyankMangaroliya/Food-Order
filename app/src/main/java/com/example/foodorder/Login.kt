package com.example.foodorder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    private var loginAttempts = 1
    private lateinit var clickSignUp: TextView
    private lateinit var SignIn: Button
    private lateinit var edContactNumber: EditText
    private lateinit var edPassword: EditText

    private lateinit var dbHelper: DBconnection

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        clickSignUp = findViewById(R.id.clickSignUp)
        SignIn = findViewById(R.id.SignIn)
        edContactNumber = findViewById(R.id.edlContact)
        edPassword = findViewById(R.id.edlPassword)

        clickSignUp.setOnClickListener {
            val signUp = Intent(this@Login, Registration::class.java)
            startActivity(signUp)
        }

        SignIn.setOnClickListener {
            val contactNumber = edContactNumber.text.toString()
            val password = edPassword.text.toString()

            if (contactNumber.isEmpty()) {
                edContactNumber.setError("Contact Number is required")
                return@setOnClickListener
            }

            if (contactNumber.length != 10) {
                edContactNumber.setError("Invalid contact number")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edPassword.setError("Password is required")
                return@setOnClickListener
            }

            if (password.length < 8) {
                edPassword.setError("Password must be at least 8 characters long")
                return@setOnClickListener
            }


            dbHelper = DBconnection(this)
            val db = dbHelper.readableDatabase

            val selection = "Contact = ?"
            val selectionArgs = arrayOf(contactNumber)

            val cursor = db.query("customer", null, selection, selectionArgs, null, null, null)

            if (cursor.moveToFirst()) {
                val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("Password"))
                var Cid = cursor.getString(cursor.getColumnIndexOrThrow("Cid"))

                if (password == storedPassword) {

                    sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("Contact", contactNumber)
                    editor.putString("Password", password)
                    editor.putString("Cid", Cid)
                    editor.apply()

                    val j = Intent(this@Login, Home::class.java)
                    startActivity(j)

                } else {
                    if (loginAttempts >= 3) {
                        Toast.makeText(
                            this@Login,
                            "You have exceeded the maximum limit for login attempts. Kindly contact your administrator!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this@Login, "Please enter valid data", Toast.LENGTH_SHORT)
                            .show()
                    }
                    loginAttempts++
                }
            } else {
                Toast.makeText(this@Login, "User not found. Please sign up.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
