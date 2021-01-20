package com.a.BilalMercan

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_sound_notification.*

class SoundNotification : AppCompatActivity() {

    lateinit var soundbtn: Button
    lateinit var stop: Button
    lateinit var songname: TextView
    lateinit var notkaydi:Button
    lateinit var backcamera:Button


    lateinit var mediaPlayer: MediaPlayer
    lateinit var manager: NotificationManager
    private val r_code = 101
    private val channelid = "com.a.BilalMercan"
    lateinit var notificationBuilder: Notification.Builder
    private val not_id = 3
    lateinit var channel: NotificationChannel




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_notification)
        backcamera = findViewById(R.id.backcamera)
        soundbtn = findViewById(R.id.soundbuton)
        songname = findViewById(R.id.songname)
        stop = findViewById(R.id.soundstop)
        notkaydi = findViewById(R.id.notepad)


        mediaPlayer = MediaPlayer()
        var progress = 0
        mediaPlayer = MediaPlayer.create(this, R.raw.scifi)


        // butona basılınca ses oynatıılıyor ve progress bar çalışıyor
        soundbuton.setOnClickListener {

            //progress 50 şer olarak artırıyor.
            if (progress < 100) {
                progress += 50
                progressBar2.progress = progress
            }
            //butona basılınca sıfırlanıp progress bar görünür hale geliyor.
            else {
                progress = 0
                progressBar2.progress = progress
                progressBar2.visibility = View.VISIBLE
            }
            //progress bar yüzde yüz dolmuşsa görünmez değerini alıyor.
            if (progress == 100) progressBar2.visibility = View.INVISIBLE

            mediaPlayer = MediaPlayer.create(this, R.raw.scifi)
            mediaPlayer.start()
            songname.text = "Şarkı Oynatılıyor..."
        }

        //butona basılınca çalınan sesi durdurma işlemi
        soundstop.setOnClickListener {
            mediaPlayer.stop()
            songname.text = "Şarkı durduruldu..."

        }

        //bildirim butonu oluşturuluyor
        val notifbuton = findViewById<Button>(R.id.notifbuton)
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //bildirim butonuna basıldığında Intent nesnesi oluşturuluyor. Pending intent ile notification için oluşturulan intent nesnesi çağırılıyor.
        //çağırılan intent nesnesini r_code önceden oluşturulan kod ile çağırılıyor.
        notifbuton.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Bildirim", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivities(this, r_code, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT) as PendingIntent

            //Bildirim adı ve bildiri mesajı içeriği
            val notificationtitle = "Bu uygulamayı kullanmaktasınız"
            val notificationbody = "Kotlin ile geliştirilmiştir"

            //Versiyon kontrolü yapılıyor SDK versiyonu 26 dan büyükse if bloğu çalışıyor küçükse else bloğu çalışıyor.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = NotificationChannel(channelid, "açıklama", NotificationManager.IMPORTANCE_HIGH)
                channel.enableLights(true)
                channel.enableVibration(true)
                channel.lightColor = Color.BLUE

                //bildirim içerisinde yer alacak içerikleri belirleniyor.
                notificationBuilder = Notification.Builder(this, channelid)
                        .setContentTitle(notificationtitle)
                        .setContentText(notificationbody)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.persona)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.persona))

                manager.createNotificationChannel(channel)
            } else {
                channel = NotificationChannel(channelid, "açıklama", NotificationManager.IMPORTANCE_HIGH)
                channel.enableLights(true)
                channel.enableVibration(true)
                channel.lightColor = Color.BLUE

                notificationBuilder = Notification.Builder(this, channelid)
                        .setContentTitle(notificationtitle)
                        .setContentText(notificationbody)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.persona)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.persona))

                manager.createNotificationChannel(channel)
            }

            manager.notify(not_id, notificationBuilder.build())

        }




        //Kamera activity'sine geçiş sağlanıyor.
        backcamera.setOnClickListener {
            val intent = Intent(this,CameraActivity::class.java)
            startActivity(intent)
            //Toast mesajı
            Toast.makeText(applicationContext, "Kamera kontrol sekmesine geçtiniz.", Toast.LENGTH_SHORT).show()
        }

        //Not kaydı activity'sine geçiş sağlanıyor.
        notkaydi.setOnClickListener {
            val intent = Intent(this,Notepad::class.java)
            startActivity(intent)
            //Toast mesajı
            Toast.makeText(applicationContext, "Not kaydı bölümüne geçtiniz.", Toast.LENGTH_SHORT).show()

        }

    }
}


