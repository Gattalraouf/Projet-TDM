package com.gattal.asta.mobileproject.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.gattal.asta.mobileproject.R
import com.gattal.asta.mobileproject.data.Product
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var number: TextView
    private lateinit var pic: ImageView
    private lateinit var desc: TextView
    private lateinit var owner: TextView
    private lateinit var productName: TextView
    private lateinit var productwilaya: TextView
    private lateinit var emailSend: ImageButton
    private lateinit var call: ImageButton
    private lateinit var product: Product

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
        pic = imageView2
        desc = textView6
        owner = textView7
        productName = textView5
        emailSend = sendEmailImageButton
        call = callOwnerImageButtton
        productwilaya = ProductwilayaName

        val intent = intent
        product = intent.getSerializableExtra("product") as Product

        for (i in product.imgs) {
            imageList.add(SlideModel(i))
        }
        imageSlider.setImageList(imageList)

        email.text = product.owner.email
        number.text = product.owner.phone
        desc.text = product.descr
        owner.text = product.owner.name
        productName.text = product.name
        dateText.text=product.Datedep
        productwilaya.text= product.Wilaya

        Glide.with(this)
            .load(product.owner.pic)
            .apply(RequestOptions.circleCropTransform())
            .into(pic)

        emailSend.setOnClickListener {
            val email1 = product.owner.email
            val subject = "Contact about ${product.name}"
            val chooserTitle = "Send Email"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email1"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            startActivity(Intent.createChooser(emailIntent, chooserTitle))
        }

        call.setOnClickListener {

            onCallBtnClick("tel:${product.owner.phone}")

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        var permissionGranted = false
        when (requestCode) {
            9 -> permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (permissionGranted) {
            phoneCall("tel:${product.owner.phone}")
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
