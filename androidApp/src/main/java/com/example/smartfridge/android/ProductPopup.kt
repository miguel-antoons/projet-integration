package com.example.smartfridge.android

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smartfridge.android.adapter.ProductAdapter
import com.example.smartfridge.android.fragments.FragmentProduct
import java.text.SimpleDateFormat
import java.util.*

class ProductPopup(
    private val adapter: ProductAdapter,
    private val selectedProduct: ProductModel,
    private val productPosition: Int,
    private val productFragment: FragmentProduct
): Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // show the pop-up on screen
        setContentView(R.layout.product_popup)

        // setup different parts of the pop-up
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupModifyButton(productPosition)
    }

    // fill the pop-up textfield with correct values
    private fun setupComponents() {
        // get the different 'TextView' elements by their id
        val productTitle = findViewById<TextView>(R.id.product_title)
        val productStatus = findViewById<ImageView>(R.id.product_status)
        val expirationDate = findViewById<TextView>(R.id.expiration_date)
        val productQuantity = findViewById<TextView>(R.id.quantity)
        val productCategory = findViewById<TextView>(R.id.category)
        val productLocation = findViewById<TextView>(R.id.product_location)

        productStatus.setColorFilter(Color.parseColor(selectedProduct.productColor))
        productTitle.text = selectedProduct.name
        expirationDate.text = SimpleDateFormat("dd / MM / yyyy", Locale.getDefault())
            .format(selectedProduct.expirationDate)
        productQuantity.text = selectedProduct.quantity.toString()
        productCategory.text = selectedProduct.category
        productLocation.text = selectedProduct.location
    }

    // close the pop-up when clicking on the close button
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_popup).setOnClickListener {
            dismiss()
        }
    }

    // delete an item when clicking on the delete button
    private fun setupDeleteButton() {
        findViewById<Button>(R.id.delete_button).setOnClickListener {
            // delete the item in the shared product list (cf. './ProductRepository.kt')
            ProductRepository.deleteProduct(productPosition)
            // notify the adapter that an item has been removed from the list
            adapter.notifyItemRemoved(productPosition)
            // close the pop-up
            dismiss()
        }
    }

    // add action on button 'modify_button'
    private fun setupModifyButton(productPosition: Int) {
        findViewById<Button>(R.id.modify_button).setOnClickListener {
            // open the form to modify the product
            productFragment.modifyProductForm(productPosition)
            // close the pop-up
            dismiss()
        }
    }
}
