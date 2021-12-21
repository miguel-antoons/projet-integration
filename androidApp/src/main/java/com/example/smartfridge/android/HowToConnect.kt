package com.example.smartfridge.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class HowToConnect : AppCompatActivity() {

    // Initialisation of the data for the QR code
    private lateinit var ivQRcode: ImageView
    private lateinit var etData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_connect)

        // Button event
        val button_return = findViewById<ImageButton>(R.id.return_icon)
        button_return.setOnClickListener {
            // end the activity and return to the previous fragment
            finish()
        }

        qrCode()
    }

    // QR code generating
    private fun qrCode () {
        etData = loadUsername()
        ivQRcode = findViewById(R.id.ivQRcode)

        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(etData, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until height) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            ivQRcode.setImageBitmap(bmp)

        } catch (e : WriterException) {
            e.printStackTrace()
        }
    }

    // Load username
    private fun loadUsername() : String {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)

        return savedUsername.toString()
    }
}