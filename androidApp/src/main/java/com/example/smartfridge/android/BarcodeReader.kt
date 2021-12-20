package com.example.smartfridge.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_barcode_reader.*
import org.json.JSONObject
import java.util.concurrent.Executors


class BarcodeReader : AppCompatActivity() {

    private var previewView: PreviewView? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector? = null
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_reader)
        val button_return = findViewById<ImageButton>(R.id.button_return_scan)
        button_return.setOnClickListener {
            // end the activity and return to the previous fragment
            finish()
        }
        setupCamera()


    }


    private fun setupCamera() {
        previewView = findViewById(R.id.preview_view)
        // Camera arriere
        cameraSelector = CameraSelector.Builder().requireLensFacing(LENS_FACING_BACK).build()
        ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CameraXViewModel::class.java)
            .processCameraProvider
            .observe(this) { provider: ProcessCameraProvider? ->
                cameraProvider = provider
                if (isCameraPermissionGranted()) {
                    bindCameraUseCases()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        PERMISSION_CAMERA_REQUEST
                    )
                }
            }
    }
    private fun bindCameraUseCases() {
        bindPreviewUseCase()
        bindAnalyseUseCase()
    }


    private fun bindPreviewUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider!!.unbind(previewUseCase)
        }

        previewUseCase = Preview.Builder()
            .setTargetRotation(previewView!!.display.rotation)
            .build()
        previewUseCase!!.setSurfaceProvider(previewView!!.surfaceProvider)

        try {

            cameraProvider!!.bindToLifecycle(
                this,
                cameraSelector!!,
                previewUseCase
            )
        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    private fun bindAnalyseUseCase() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_EAN_8, Barcode.FORMAT_EAN_13).build()

        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(options)

        if (cameraProvider == null) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider!!.unbind(analysisUseCase)
        }

        analysisUseCase = ImageAnalysis.Builder()
            .setTargetRotation(previewView!!.display.rotation)
            .build()

        // Initialize our background executor
        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(
            cameraExecutor,
            { imageProxy ->
                processImageProxy(barcodeScanner, imageProxy)
            }
        )

        try {
            cameraProvider!!.bindToLifecycle(
                /* lifecycleOwner= */this,
                cameraSelector!!,
                analysisUseCase
            )
        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    @SuppressLint("UnsafeOptInUsageError", "SetTextI18n")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->

                    val url = "https://world.openfoodfacts.org/api/v0/product/${barcode.rawValue}"
                    val queue = Volley.newRequestQueue(this)
                    val stringRequest = StringRequest(
                        Request.Method.GET, url,
                        { response ->
                            // Display the first 500 characters of the response string.
                            Log.d("Ingredients", response)
                            val json = JSONObject(response)
                            if (json.has("product")){

                                if(json.getJSONObject("product").has("ingredients_text_fr")){
                                    val result = json.getJSONObject("product").getString("ingredients_text_fr")
                                    title_scan.text = result
                                }
                                else{
                                    title_scan.text = "Le produit a été trouvé mais sa liste d'ingrédients n'est pas à jour !"
                                }
                            }
                            else{
                                title_scan.text = "Le produit est introuvable ou le code barre est illisible !"
                            }

                        },
                        { title_scan.text = "That didn't work!" })

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest)
                }

            }
            .addOnFailureListener {
                Log.e(TAG, it.message ?: it.toString())
            }
            .addOnCompleteListener {
                // When the image is from CameraX analysis use case, must call image.close() on received
                // images when finished using them. Otherwise, new images may not be received or the camera
                // may stall.
                imageProxy.close()

            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (isCameraPermissionGranted()) {
                bindCameraUseCases()
            } else {
                Log.e(TAG, "no camera permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        private const val PERMISSION_CAMERA_REQUEST = 1
    }







}