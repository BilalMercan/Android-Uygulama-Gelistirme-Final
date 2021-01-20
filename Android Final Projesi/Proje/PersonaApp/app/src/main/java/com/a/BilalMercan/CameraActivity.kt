package com.a.BilalMercan

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.camera_main.*
import java.io.ByteArrayOutputStream


class CameraActivity : AppCompatActivity() {
    val REQUESTCODE_CAMERA = 1
    var imageArray=ArrayList<ByteArray>()
    private var adapter: GridViewAdapter? = null
    lateinit var Kamera: Button
    lateinit var soundcontrol: Button
    lateinit var anaekran: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_main)
        Kamera = findViewById(R.id.Kamerabuton)
        soundcontrol = findViewById(R.id.soundcontrol)
        anaekran = findViewById(R.id.anaekran)

        //Kamera butonuna basıldığında izinler istenir izinler verilirse kamera açılır ve fotoğraf çekebilir.
        Kamerabuton.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permission, REQUESTCODE_CAMERA)
            } else
                takePhotoFromCamera()
        }

        //Ses kontrol activity ' sine geçiş
        soundcontrol.setOnClickListener {
            val intent = Intent(this,SoundNotification::class.java)
            startActivity(intent)
            //Toast mesajı
            Toast.makeText(applicationContext, "Ses kontrol sekmesine geçtiniz.", Toast.LENGTH_SHORT).show()
        }



        anaekran.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Ana ekrana geçtiniz.", Toast.LENGTH_SHORT).show()
        }



    }


    //tanımlanan kamera kodu ile kameraya ulaşılır.
    private fun takePhotoFromCamera()
    {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUESTCODE_CAMERA)
            }
        }

    }

    //Bitmap ile fotoğraf jpeg dosyasına dönüştürülür. Çekilen fotoğraflar için array oluşturulur ve içerisine eklenir
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUESTCODE_CAMERA)
        {
            val imageBitmap = intent?.extras!!.get("data") as Bitmap
            val bytes = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
            imageArray.add(bytes.toByteArray())
            bytes.close()
        }

        //Array içerisindeki fotoğraflar gridview ekranına gönderilir.
        adapter = GridViewAdapter(this, imageArray)
        GridView.adapter = adapter
    }

}
