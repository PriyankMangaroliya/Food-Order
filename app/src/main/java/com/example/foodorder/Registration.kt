package com.example.foodorder

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registration : AppCompatActivity() {

    private lateinit var edFullName: EditText
    private lateinit var edContactNumber: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edAddress: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var clickSignIn: TextView
    private lateinit var btn: Button

    private lateinit var dbHelper: DBconnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        edFullName = findViewById(R.id.edname)
        edEmail = findViewById(R.id.edemail)
        edContactNumber = findViewById(R.id.edcontact)
        edAddress = findViewById(R.id.edaddress)
        edPassword = findViewById(R.id.edPassword)
        edConfirmPassword = findViewById(R.id.edCPassword)
        clickSignIn = findViewById(R.id.clickSignIn)
        btn = findViewById(R.id.Signup)

        clickSignIn.setOnClickListener {
            val signIn = Intent(this@Registration, Login::class.java)
            startActivity(signIn)
        }

        dbHelper = DBconnection(this)

        btn.setOnClickListener {
            val address = edAddress.text.toString()
            val fullName = edFullName.text.toString()
            val email = edEmail.text.toString()
            val contactNumber = edContactNumber.text.toString()
            val password = edPassword.text.toString()
            val confirmPassword = edConfirmPassword.text.toString()

            if (fullName.isEmpty()) {
                edFullName.error = "Full Name is required"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                edEmail.error = "Email is required"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edEmail.error = "Invalid email format"
                return@setOnClickListener
            }

            if (contactNumber.isEmpty()) {
                edContactNumber.error = "Contact Number is required"
                return@setOnClickListener
            }

            if (contactNumber.length != 10) {
                edContactNumber.error = "Invalid contact number"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edPassword.error = "Password is required"
                return@setOnClickListener
            }

            if (password.length < 8) {
                edPassword.error = "Password must be at least 8 characters long"
                return@setOnClickListener
            }

            if (confirmPassword != password) {
                edConfirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put("Name", fullName)
                put("Email", email)
                put("Address", address)
                put("Contact", contactNumber)
                put("Password", password)
            }

            val newRowId = db.insert("customer", null, values)
            db.close()

            if (newRowId != -1L) {
                Toast.makeText(this, "User Registration Successfully", Toast.LENGTH_SHORT).show()
                val i = Intent(this@Registration, Login::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "Not Registration, Please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}