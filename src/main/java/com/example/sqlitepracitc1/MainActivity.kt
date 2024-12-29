package com.example.sqlitepracitc1

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.walletapp.ProductAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var productList: ListView
    private lateinit var productName: EditText
    private lateinit var productWeight: EditText
    private lateinit var productPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        dbHelper = DBHelper(this)

        productList = findViewById(R.id.productList)
        productName = findViewById(R.id.productName)
        productWeight = findViewById(R.id.productWeight)
        productPrice = findViewById(R.id.productPrice)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveProduct()
        }

        loadProducts()
    }

    private fun saveProduct() {
        val name = productName.text.toString()
        val weight = productWeight.text.toString().toFloatOrNull() ?: return
        val price = productPrice.text.toString().toFloatOrNull() ?: return

        dbHelper.addProduct(name, weight, price)
        loadProducts()


        productName.text.clear()
        productWeight.text.clear()
        productPrice.text.clear()
    }

    @SuppressLint("Range")
    private fun loadProducts() {
        val cursor: Cursor = dbHelper.getAllProducts()
        val productListData = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                val weight = cursor.getFloat(cursor.getColumnIndex(DBHelper.KEY_WEIGHT))
                val price = cursor.getFloat(cursor.getColumnIndex(DBHelper.KEY_PRICE))

                productListData.add(Product(name, weight, price))
            } while (cursor.moveToNext())
        }
        cursor.close()

        val adapter = ProductAdapter(this, productListData)
        productList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}