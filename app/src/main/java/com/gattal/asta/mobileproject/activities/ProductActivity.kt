package com.gattal.asta.mobileproject.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.gattal.asta.mobileproject.R
import com.gattal.asta.mobileproject.modeldata.AdEntity
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var number: TextView
    private lateinit var number2: TextView
    private lateinit var categorya: TextView
    private lateinit var desc: TextView
    private lateinit var owner: TextView
    private lateinit var type: TextView
    private lateinit var productwilaya: TextView
    private lateinit var addressa: TextView
    private lateinit var surfacea: TextView
    private lateinit var pricea: TextView
    private lateinit var emailSend: ImageButton
    private lateinit var call: ImageButton
    private lateinit var call2: ImageButton
    private lateinit var position: ImageButton
    private lateinit var position2: ImageButton
    private lateinit var product: AdEntity
    private lateinit var video: VideoView

    private lateinit var sms: ImageButton
    private lateinit var mail: ImageButton

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val imageList = ArrayList<SlideModel>()
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)

        email = emailCorps
        number = PhoneNumber
        categorya = category
        addressa = owner_adress
        surfacea = surface
        pricea = price
        desc = textView6
        owner = textView7
        type = textView5
        emailSend = sendEmailImageButton
        call = callOwnerImageButtton
        productwilaya = ProductwilayaName
        position = PositionImageButtton
        position2 = adress_button
        video = videoView

        number2 = PhoneNumber2
        call2 = callOwnerImageButtton2
        sms = share_sms
        mail = share_email


        val intent = intent
        product = intent.getSerializableExtra("annonce") as AdEntity

        for (i in product.imgs) {
            imageList.add(SlideModel(i))
        }
        imageSlider.setImageList(imageList)

        email.text = product.ownerMail
        number.text = product.ownerPhone
        //number2.text = product.owner.phone2
        categorya.text = product.category
        addressa.text = product.ownerAddress
        surfacea.text = product.area
        pricea.text = product.price + " DA"
        desc.text = product.descript
        owner.text = product.ownerName
        type.text = product.type
        productwilaya.text = product.wilaya

        emailSend.setOnClickListener {
            val email1 = product.ownerMail
            val subject = "Contact about ${product.type}"
            val chooserTitle = "Send Email"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email1"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            startActivity(Intent.createChooser(emailIntent, chooserTitle))
        }

        call.setOnClickListener {

            onCallBtnClick("tel:${product.ownerPhone}")

        }

        call2.setOnClickListener {

            onCallBtnClick("tel:${product.ownerPhone}")

        }

        sms.setOnClickListener {
            val uri = Uri.parse("smsto:")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", product.link)
            startActivity(intent)
        }

        mail.setOnClickListener {

            val subject = "Shared Offer link"
            val chooserTitle = "Offer Link"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, product.link)
            startActivity(Intent.createChooser(emailIntent, chooserTitle))
        }


        position.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${productwilaya.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }

        position2.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${addressa.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }


    }


    private fun onCallBtnClick(tel: String) {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(tel)
        } else {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                phoneCall(tel)
            } else {
                val permissionStorage = arrayOf(Manifest.permission.CALL_PHONE)
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, permissionStorage, 9)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        var permissionGranted = false
        when (requestCode) {
            9 -> permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (permissionGranted) {
            phoneCall("tel:${product.ownerPhone}")
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun phoneCall(tel: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(tel)
            this.startActivity(callIntent)
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
