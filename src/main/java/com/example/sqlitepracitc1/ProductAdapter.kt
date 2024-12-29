package com.example.walletapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sqlitepracitc1.Product
import com.example.sqlitepracitc1.R

class ProductAdapter(context: Context, private val products: List<Product>) : ArrayAdapter<Product>(context, 0, products) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val product = getItem(position)

        val listItem = convertView ?: LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)

        val nameTextView = listItem.findViewById<TextView>(R.id.textName)
        val weightTextView = listItem.findViewById<TextView>(R.id.textWeight)
        val priceTextView = listItem.findViewById<TextView>(R.id.textPrice)

        nameTextView.text = product?.name
        weightTextView.text = "Вес: ${product?.weight} кг"
        priceTextView.text = "Цена: ${product?.price} рублей"

        return listItem
    }
}