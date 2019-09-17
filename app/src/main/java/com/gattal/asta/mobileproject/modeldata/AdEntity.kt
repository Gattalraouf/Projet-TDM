package com.gattal.asta.mobileproject.modeldata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "ad_table")
class AdEntity : Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title : String = "Annonce"

    @ColumnInfo(name = "sate")
    var state : Int = -1

    @ColumnInfo(name = "link")
    var link: String = "https://------------"

    @ColumnInfo(name = "category")
    var category : String = "Entreprise"

    @ColumnInfo(name = "type")
    var type : String = "Vente"

    @ColumnInfo(name = "address")
    var address : String = "----"

    @ColumnInfo(name = "date")
    var date : String = "--/--/----"

    @ColumnInfo(name = "wilaya")
    var wilaya : String = "----"

    @ColumnInfo(name = "area")
    var area : String = "----"

    @ColumnInfo(name = "price")
    var price : String = "----"

    @ColumnInfo(name = "description")
    var descript : String = "----"

    @ColumnInfo(name = "ownerName")
    var ownerName : String = "----"

   // @ColumnInfo(name = "ownerLastname")
  //  var sellerLastName : String = "----"

    @ColumnInfo(name = "ownerAddress")
    var ownerAddress : String = "----"

    @ColumnInfo(name = "ownerPhone")
    var ownerPhone : String = "----"

    @ColumnInfo(name = "ownerMail")
    var ownerMail : String = "----"

    @ColumnInfo(name = "imgs")
    var imgs: List<String> =listOf("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_01_0000_max_656x437.jpg")
}
