package com.a.BilalMercan

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sound_notification.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class personapicall : AppCompatActivity() {

     //Hava durumu bilgileri ve api openweathermap sitesinden alınmıştır
    //Hava durumu istenen şehri ve ülke kodu aralarında virgül ile ayırarak yazılıyor.
    val CITY: String = "Istanbul,tr"
    //Api Key ile alınan verilerin iletişimi bu key ile sağlanıyor
    val API: String = "7b04a12368174adb61671d697504e367"

    //Bilal Mercan Api Key = 7b04a12368174adb61671d697504e367

    lateinit var sqlislem:Button
    lateinit var notlar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personapicall)

        sqlislem = findViewById(R.id.sqlislem)
        notlar = findViewById(R.id.notlar)
        sqlislem.setOnClickListener {
            val intent = Intent (this,personsqlcrud::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Veri tabanı işlemleri", Toast.LENGTH_SHORT).show()

        }

        notlar.setOnClickListener {
            val intent = Intent (this,Notepad::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Not kaydı", Toast.LENGTH_SHORT).show()
        }

        HavaDurumu().execute()
    }

    //yeni sınıf tanımlanıyor ve üst sınıftan miras alınıyor.
    //AsyncTask soyut sınıfı ile işlemler arkaplanda yapılması sağlanıyor.
    inner class HavaDurumu : AsyncTask<String, Void, String>() {

         //doInBackGround metodu ile arka planda ulaşılması hedeflenen internet sitesine ulaşılıyor ve readText(Charsets.UTF_8) ile
         //ulaşılan sayfadan verileri UTF_8 formatı ile yazı tipinden okunuyor.
         //vararg parametresi ile birden fazla farklı paratmetreleri istenilen sayıda kullanabiliyor.

        override fun doInBackground(vararg params: String?): String? {
            val response:String?
            response = try{
                URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)
            }catch (e: Exception){
                null
            }
            return response
        }

        //doInBackground işlemleri tamamlandıktan sonra işlemlerin sonucu onPostExecute metoduna gelir.
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //try catch bloğu ile try bloğu yapılacak işlemleri, catch bloğu hata yakaladığı zaman çalışması sağlanır.
            try {
                //json objesi oluşturularak verilerin json dosyasından alınması sağlanır.
                    //openweathermap sitesinden api anahtarı ile alınan verilere ulaşılması sağlanıyor.
                val jsonObjesi = JSONObject(result)
                val main = jsonObjesi.getJSONObject("main")
                val sys = jsonObjesi.getJSONObject("sys")
                val weather = jsonObjesi.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObjesi.getLong("dt")
                val guncellenme = "Güncellenme Zamanı: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ITALY).format(Date(updatedAt*1000))
                val derece = main.getString("temp")+"°C"
                val durum = weather.getString("description")

                //şehir ve ülke bilgilerinin yazdırılması
                val bolge = jsonObjesi.getString("name")+", "+sys.getString("country")

                //alınan verilerin textview ile eşitlenmesi
                findViewById<TextView>(R.id.bolge).text = bolge
                findViewById<TextView>(R.id.saatguncelleme).text =  guncellenme
                findViewById<TextView>(R.id.havanasil).text = durum.capitalize(Locale.ROOT)
                findViewById<TextView>(R.id.derece).text = derece

            } catch (e: Exception) {
                //Hata alınması durumunda toast mesajı çalışarak uyarı vermesi sağlanıyor.
              Toast.makeText(applicationContext, "Bir Hata ile karşılaşıldı...", Toast.LENGTH_SHORT).show()
            }

        }
    }



}