package com.a.BilalMercan

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_personsqlcrud.*
import kotlin.collections.ArrayList


val DATABASENAME = "Personas"
val TABLENAME = "Persona"
val COL_NAME = "name"
val COL_SURNAME = "surname"
val COL_AGE = "age"
val COL_ID = "id"
val COL_NUMBER = "number"
val COL_MAIL = "mail"
val COL_ADRESS = "adresi"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
         //Tablo oluşturma sorgusu sütun adları ve alacağı değerler yazıldıktan sonra tablo oluşturulur.
        val createTable = "CREATE TABLE " + TABLENAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " VARCHAR(25), " + COL_SURNAME + " VARCHAR(25), " + COL_AGE + " INTEGER, " + COL_NUMBER + " INTEGER, " + COL_MAIL + " VARCHAR(25), " + COL_ADRESS + " VARCHAR(25) )"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        onCreate(db)
    }

    //person classından alınan değikenleri sql sorgusunda güncellemek için kullanılıyor.
    //conntentvalues ile alınan değerlerin hangi sütuna yerleştirileceği belirtiliyor.
    fun updateData(person: Person) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Persona SET  name= '${person.name}', surname = '${person.surname}', mail = '${person.mail}', age = '${person.age}', number = '${person.number}',  adresi = '${person.adresi}' WHERE id = (SELECT MAX(id) FROM Persona) ")
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, person.name)
        contentValues.put(COL_SURNAME, person.surname)
        contentValues.put(COL_MAIL, person.mail)
        contentValues.put(COL_AGE, person.age).toString()
        contentValues.put(COL_NUMBER, person.number).toString()
        contentValues.put(COL_ADRESS, person.adresi)

    }


    fun deleteData() {
        //this.writabledatabase ile database üzerinde işlem yapılmasını sağlıyor.
        val db = this.writableDatabase
         //execsql ile sql sorgusu çalıştırılıyor. tablodaki en yüksek id si olan satırın silinmesi sağlanıyor
         db.execSQL("DELETE FROM $TABLENAME WHERE id = (SELECT MAX(id) FROM $TABLENAME)")
        //db.delete(TABLENAME, null, null)
        db.close()
    }

      //Person class ında oluşturulan değişkenlerin sütunlara eşitlenerek eklenecek verilerin hangi dağişkene ekleneceği belirleniyor.
      fun insertData(person:Person) {
         //writableDatabase ile database üzerine veri yazılması sağlanıyor.
        val database = this.writableDatabase
         //Content values key/value değerlerini tutan key tablo sütunu adını value ise sütunların aldığı değerleri belirtir.
          //put ile veri gönderilmesi sağlanır.
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, person.name)
        contentValues.put(COL_AGE, person.age)
        contentValues.put(COL_SURNAME, person.surname)
        contentValues.put(COL_NUMBER, person.number)
        contentValues.put(COL_MAIL, person.mail)
        contentValues.put(COL_ADRESS, person.adresi)

          //database.insert komutu ile database e verigirişi sağlanıyor. alacağı parametreler tabloadi, null ,contenvalues
          val result = database.insert(TABLENAME, null, contentValues)
          //eğer tablo sütunlar boş ise kullanıoı eklenemedi mesajı veriyor.
          if (result == 0L) {
              Toast.makeText(context, "Kullanıcı eklenemedi", Toast.LENGTH_SHORT).show()
          } else {
              Toast.makeText(context, "Kullanıcı eklendi", Toast.LENGTH_SHORT).show()
          }


      }

         //Person class ını parametre belirterek hangi değişkenlerin okunacağı belirleniyor.
      fun readData(): MutableList<Person> {
        //this.readableDatabase ile database den alınan verilerin okunabilmesi sağlanıyor.
        //sql sorgusu ile tablodaki bütün verinin alınması sağlanıyor.
        //result ile sql sorgusunu çalıştırıyor.
        val list: MutableList<Person> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from  $TABLENAME"
        val result = db.rawQuery(query, null)
           //do while döngüsü ile if koşulu ile eğer ilk eklenen veri ise verileri sütunlara göre yerleştiriyor.
           //person dizisine sırası ile ekleniyor. while ile eklenen veriler oldukça sql sorgusunu tekrar çalıştırıp bütün
           //verilerin okunması sağlanır. Döngüde olduğu için list değişkenini yani Person arraylistini geri döndürmesi gerekiyor.
        if (result.moveToFirst()){

            do {
                 //Person class ından alınan değişkenlerin okunması ve sütunlarına göre ayrılması sağlanıyor.
                val id : Int = result.getString(result.getColumnIndex(COL_ID)).toInt()
                val name : String = result.getString(result.getColumnIndex(COL_NAME))
                val surname : String = result.getString(result.getColumnIndex(COL_SURNAME))
                val age :Int = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                val number:Int = result.getString(result.getColumnIndex(COL_NUMBER)).toInt()
                val mail : String = result.getString(result.getColumnIndex(COL_MAIL))
                val adresi : String = result.getString(result.getColumnIndex(COL_ADRESS))


               val person = Person(id, name, surname, age, number, mail, adresi)
                list.add(person)

            }while (result.moveToNext())
        }
          return list
      }


    }
