package com.example.foodorder

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBconnection(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "food1.db"
        private const val DATABASE_VERSION = 1
    }

    private val customer_table = """
        CREATE TABLE customer (
            Cid INTEGER PRIMARY KEY AUTOINCREMENT,
            Name TEXT,
            Email TEXT,
            Address TEXT,
            Contact TEXT,
            Password TEXT
        );
    """.trimIndent()

    private val food_table = """
        CREATE TABLE food (
            Fid INTEGER PRIMARY KEY AUTOINCREMENT,
            Name TEXT,
            Price Integer,
            Image TEXT,
            Description TEXT,
            City TEXT
        );
    """.trimIndent()


    private val order_table = """
        CREATE TABLE orders (
            Oid INTEGER PRIMARY KEY AUTOINCREMENT,
            Fid INTEGER,
            Cid INTEGER,
            Qty INTEGER,
            Total_amount INTEGER,
            FOREIGN KEY (Fid) REFERENCES food(Fid),
            FOREIGN KEY (Cid) REFERENCES customer(Cid)
        );
    """.trimIndent()

    val values11 = ContentValues().apply {
        put("Name", "Pizza")
        put("Price", 100)
        put(
            "Image",
            "https://cdn-fjknb.nitrocdn.com/sYJEeGkHVBiftWuTfZsKGWIHyaufcHUS/assets/images/optimized/rev-4fbea7c/wp-content/uploads/2022/03/Italian-Chicken-Pizza-Recipe-585x439.jpg"
        )
        put("Description", "Italian Pizza")
        put("City", "Surat")
    }
    val values12 = ContentValues().apply {
        put("Name", "Burger")
        put("Price", 80)
        put("Image", "https://natashaskitchen.com/wp-content/uploads/2023/06/Cheeseburger.jpg")
        put("Description", "Cheese burger")
        put("City", "Surat")
    }
    val values13 = ContentValues().apply {
        put("Name", "Dabeli")
        put("Price", 40)
        put("Image", "https://samosastreet.com/wp-content/uploads/Dabeli-4.jpg")
        put("Description", "Cheese Dabeli")
        put("City", "Surat")
    }
    val values14 = ContentValues().apply {
        put("Name", "Pav Bhaji")
        put("Price", 100)
        put(
            "Image",
            "https://amritsruae.com/blog/wp-content/uploads/2022/03/Pav-Bhaji-870x635-1-825x510.jpg"
        )
        put("Description", "Cheese Pav Bhaji")
        put("City", "Surat")
    }
    val values15 = ContentValues().apply {
        put("Name", "Mansurian")
        put("Price", 100)
        put(
            "Image",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWzur8bIkMUlf5Gg5G6WJ5E_b4zeW4OIHs4g&usqp=CAU"
        )
        put("Description", "Veg. Mansurian")
        put("City", "Surat")
    }
    val values16 = ContentValues().apply {
        put("Name", "Franki")
        put("Price", 60)
        put(
            "Image",
            "https://www.nehascookbook.com/wp-content/uploads/2022/10/Schezwan-frankiee-WS.jpg"
        )
        put("Description", "Schezwan Frankie")
        put("City", "Surat")
    }

    val values21 = ContentValues().apply {
        put("Name", "Pizza")
        put("Price", 100)
        put("Image", "https://static.toiimg.com/thumb/53110049.cms?width=1200&height=900")
        put("Description", "Italian Pizza")
        put("City", "Bardoli")
    }
    val values22 = ContentValues().apply {
        put("Name", "Burger")
        put("Price", 80)
        put(
            "Image",
            "https://cdn.cnn.com/cnnnext/dam/assets/230711092413-burger-king-real-cheeseburger-full-169.jpeg"
        )
        put("Description", "Cheese burger")
        put("City", "Bardoli")
    }
    val values23 = ContentValues().apply {
        put("Name", "Mansurian")
        put("Price", 100)
        put(
            "Image",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWzur8bIkMUlf5Gg5G6WJ5E_b4zeW4OIHs4g&usqp=CAU"
        )
        put("Description", "Veg. Mansurian")
        put("City", "Bardoli")
    }
    val values24 = ContentValues().apply {
        put("Name", "Dabeli")
        put("Price", 40)
        put("Image", "https://samosastreet.com/wp-content/uploads/Dabeli-4.jpg")
        put("Description", "Cheese Dabeli")
        put("City", "Bardoli")
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(customer_table)
        db.execSQL(food_table)
        db.execSQL(order_table)

        db.insert("food", null, values11)
        db.insert("food", null, values12)
        db.insert("food", null, values13)
        db.insert("food", null, values14)
        db.insert("food", null, values15)
        db.insert("food", null, values16)

        db.insert("food", null, values21)
        db.insert("food", null, values22)
        db.insert("food", null, values23)
        db.insert("food", null, values24)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS customer")
        db.execSQL("DROP TABLE IF EXISTS food")
        db.execSQL("DROP TABLE IF EXISTS orders")
    }
}