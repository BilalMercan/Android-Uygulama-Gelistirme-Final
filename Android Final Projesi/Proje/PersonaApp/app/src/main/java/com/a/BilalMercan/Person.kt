package com.a.BilalMercan

class Person {
    var id:Int = 0
    var name:String? = null
    var surname:String? = null
    var age:Int = 0
    var number:Int= 0
    var mail :String? = null
    var adresi:String? = null
     //Değişkenlerin tutulduğu, verilerin gönderildiği değişkenler
    //kurucu metod ile ilk başlatılacak metod oluşturuluyor.
    constructor(id:Int, name:String, surname:String, age:Int, number:Int, mail:String, adresi:String){
        this.id = id
        this.name = name
        this.surname = surname
        this.age = age
        this.number = number
        this.mail = mail
        this.adresi = adresi


      }

    constructor(name:String, surname: String, age: Int, number: Int, mail: String, adresi: String){
        this.name = name
        this.surname = surname
        this.age = age
        this.number = number
        this.mail = mail
        this.adresi = adresi
    }



}