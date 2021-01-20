package com.a.BilalMercan


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import kotlinx.android.synthetic.main.activity_personsqlcrud.*
class personsqlcrud : AppCompatActivity() {

    lateinit var anaekran: Button
    lateinit var personekle: Button
    lateinit var sil: Button
    lateinit var guncelle: Button
    lateinit var listele: Button
    lateinit var hd: Button
    lateinit var adtext: EditText
    lateinit var soyadtext: EditText
    lateinit var yastext: EditText
    lateinit var adrestext: EditText
    lateinit var telefontext: EditText
    lateinit var emailtext: EditText
    lateinit var PersonTV: TextView


    private val db = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personsqlcrud)
        anaekran = findViewById(R.id.Anaekrann)
        hd = findViewById(R.id.hd)
        personekle = findViewById(R.id.Personadd)
        sil = findViewById(R.id.Persondelete)
        guncelle = findViewById(R.id.Personupdate)
        listele = findViewById(R.id.PersonList)
        PersonTV = findViewById(R.id.Persontextview)
        adtext = findViewById(R.id.edtad)
        soyadtext = findViewById(R.id.edtsoyad)
        yastext = findViewById(R.id.edtyas)
        adrestext = findViewById(R.id.edtadres)
        telefontext = findViewById(R.id.edttelefon)
        emailtext = findViewById(R.id.edtmail)

        PersonTV.movementMethod = ScrollingMovementMethod()

        personekle.setOnClickListener {

            if (edtad.text.toString().isEmpty() || edtsoyad.text.toString().isEmpty() || edtyas.text.toString().isEmpty() || edttelefon.text.toString().isEmpty() || edtmail.text.toString().isEmpty() || edtadres.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Lütfen tüm bilgilerinizi doldurun", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val person = Person(edtad.text.toString(), edtsoyad.text.toString(), edtyas.text.toString().toInt(), edttelefon.text.toString().toInt(), edtmail.text.toString(), edtadres.text.toString())
            db.insertData(person)
        }


        listele.setOnClickListener {

            val data = db.readData()
            PersonTV.text = ""

            for (i in 0 until data.size) {
                PersonTV.append(data[i].id.toString() + "  -  " + data[i].name.toString() + "  -  " + data[i].surname.toString() + "  -  " + data[i].age.toString().toInt() + "\n" + "  -  " + data[i].number.toString().toInt() + "\n" + "  -  " + data[i].mail.toString() + "  -  " + data[i].adresi.toString() + " " +" \n")
            }

        }


        guncelle.setOnClickListener {
            val person = Person(edtad.text.toString(), edtsoyad.text.toString(), edtyas.text.toString().toInt(), edttelefon.text.toString().toInt(), edtmail.text.toString(), edtadres.text.toString())
            db.updateData(person)
            Toast.makeText(applicationContext, "Verileriniz güncellendi", Toast.LENGTH_SHORT).show()
        }

        sil.setOnClickListener {
            db.deleteData()
            PersonList.performClick()
            Toast.makeText(applicationContext, "En son eklenen veri silindi", Toast.LENGTH_SHORT).show()
        }

        anaekran.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Ana ekrana geçildi", Toast.LENGTH_SHORT).show()
        }

        hd.setOnClickListener {
            val intent = Intent(this, personapicall::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Hava Durumu", Toast.LENGTH_SHORT).show()
        }
    }
}

