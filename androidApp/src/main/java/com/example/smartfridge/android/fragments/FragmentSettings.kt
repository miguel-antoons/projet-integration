package com.example.smartfridge.android.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class FragmentSettings(private val context: MainActivity) : Fragment()  {
    // Initialisation of the data for the QR code
    private lateinit var ivQRcode: ImageView
    private lateinit var etData : String

    // Function that displays the fragment 'FragmentProduct' on the screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val disconnect: Button = view.findViewById(R.id.disconnect)

        // create qrCode
        qrCode(view)

        val username = view.findViewById<TextView>(R.id.usernameSettings)
        username.setText(loadUsername())

        // This little lines of code set an action to the button (onClickListener)
        disconnect.setOnClickListener{
            deleteData();
            val intent = Intent(context, Login::class.java);
            startActivity(intent);
        }

        return view

    }

    // load Username
    private fun loadUsername() : String {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)

        return savedUsername.toString()
    }

    // QR code generating
    private fun qrCode (view: View) {
        etData = loadUsername()
        ivQRcode = view.findViewById(R.id.ivQRcode)

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

    // save email and password locally
    private fun deleteData() {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("EMAIL", "Email")
            putString("PASSWORD", "Password")
            putString("USERNAME", "Username")
        }.apply()
    }
}