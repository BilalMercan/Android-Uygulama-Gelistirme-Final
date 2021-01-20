package com.a.BilalMercan

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var exit:Button
    lateinit var spec:TextView
    lateinit var kgv:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          exit = findViewById(R.id.exit)
          spec = findViewById(R.id.spec)
          kgv = findViewById(R.id.kgv)

        //Kullanıcı butona bastığında Kamera activity e geçişi sağlanır.
        kgv.setOnClickListener {
            val intent = Intent(this,CameraActivity::class.java)
            startActivity(intent)

            //Toast mesajı oluşturulması
            Toast.makeText(applicationContext, "Kamera kontrol sekmesine geçtiniz.", Toast.LENGTH_SHORT).show()

        }

        //Çıkış butona tıklanıldığında kullanıcıya çıkmak istediğini sorar.
        exit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Çıkmak İstiyormusunuz ?")
            builder.setCancelable(true)
            builder.setNegativeButton("Hayır", DialogInterface.OnClickListener{ DialogInterface, _ ->
                DialogInterface.cancel() })

            builder.setPositiveButton("Evet", DialogInterface.OnClickListener{ _, _ ->
                finish()
            })

            val alertDialog = builder.create()
            alertDialog.show()
        }


    }

}