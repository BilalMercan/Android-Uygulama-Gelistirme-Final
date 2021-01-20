package com.a.BilalMercan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class Notepad : AppCompatActivity() {

    lateinit var not:EditText
    lateinit var notadi: EditText
    lateinit var yenikayit: Button
    lateinit var kaydigetir:Button
    lateinit var Havadurum:Button
    lateinit var sesbildirim:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notepad)
        sesbildirim = findViewById(R.id.sesbildirim)
        yenikayit = findViewById(R.id.yenikayıt)
        kaydigetir = findViewById(R.id.kaydigetir)
        not = findViewById(R.id.not)
        notadi = findViewById(R.id.notadi)
        Havadurum = findViewById(R.id.Havadurum)



        //kayıt butonuna tıklandığında edittext(notkaydet)'e girilen veriler notkaydet(edittext)'e kaydedilir.
        yenikayit.setOnClickListener{

            //dosya adı ve notların tanımmlanması
            val file = notadi.text.toString()
            val data = not.text.toString()

            val fileOutputStream: FileOutputStream

            try {
                //tanımlanan file ve data içerisine yazma işlemi
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            }
            catch (e: Exception){
                e.printStackTrace()

            }
            catch (e: Exception){
                e.printStackTrace()
            }
            showToast ("kaydedildi")


        }




        //kaydedilen notları getirme
        kaydigetir.setOnClickListener{
            //file içerisine yazılanları geri getiriyor.
            val filename = notadi.text.toString()

            if (filename.toString() !=null && filename.trim() !=""){

                //bufferedreader ile okuyor. fileınputstream ile dosyaya ulaşıyor.
                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput (filename)
                val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null

                //dosya içerisindeki kelime kelime okur.
                while ({text = bufferedReader.readLine(); text}() !=null){
                    //stringbuilder nesnesi ile birden fazla String kullanılabilir. append ile text değiskenine ekler.
                    stringBuilder.append(text)
                }

                //edittext'e girilen notları gösteriyoruz
                not.setText(stringBuilder.toString()).toString()

            }else
                showToast("dosya adı boş bırakılamaz.")

        }




        Havadurum.setOnClickListener {
            val intent = Intent(this,personapicall::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Anlık Hava Durumu", Toast.LENGTH_SHORT).show()
        }


       sesbildirim.setOnClickListener {
           val intent = Intent(this,SoundNotification::class.java)
           startActivity(intent)
           Toast.makeText(applicationContext, "Ses ve Bildirim Kontrolü", Toast.LENGTH_SHORT).show()
       }


    }

    //Toast mesajını fonksiyon olarak tanımlıyoruz. Böylece birden çok kullanılabilir.
    fun Context.showToast (text: CharSequence, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, text, duration).show()
    }
}