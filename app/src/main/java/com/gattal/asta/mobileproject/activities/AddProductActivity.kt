package com.gattal.asta.mobileproject.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.gattal.asta.mobileproject.data.Owner
import com.gattal.asta.mobileproject.data.Product
import com.gattal.asta.mobileproject.R
import kotlinx.android.synthetic.main.activity_add_product.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddProductActivity : AppCompatActivity() {

    private lateinit var productName: EditText
    private lateinit var productDescr: EditText
    private lateinit var productPics: EditText
    private lateinit var productPicsButton: ImageButton
    private lateinit var ownerName: EditText
    private lateinit var ownerPic: EditText
    private lateinit var ownerPicButton: ImageButton
    private lateinit var ownerEmail: EditText
    private lateinit var ownerPhone: EditText
    private lateinit var confirmButtonView: Button
    private lateinit var productObject: Product
    private lateinit var ownerObject: Owner
    private lateinit var productWilaya: EditText
    private var imgs: MutableList<String> = ArrayList()
    private lateinit var imgOwner: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        productName = productNameEditText
        productDescr = productDescEditText
        productPics = productPicLinkEditText
        productPicsButton = productPicLinkImageButton
        ownerName = ownerNameEditText
        ownerEmail = ownerEmailEditText
        ownerPhone = ownerPhoneEditText
        ownerPic = ownerPicLinkEditText
        ownerPicButton = ownerPicLinkImageButton
        productWilaya = WilayaText
        confirmButtonView = confirmButton
        imgOwner = ""
        imgs = ArrayList()

        productPicsButton.setOnClickListener {
            selectImagesInAlbum()
        }

        ownerPicButton.setOnClickListener {
            selectImageInAlbum()
        }

        confirmButtonView.setOnClickListener {
            checkEditText(productWilaya)
            checkEditText(productDescr)
            checkEditText(productName)
            checkEditText(ownerEmail)
            checkEditText(ownerName)
            checkEditText(ownerPhone)
            if (imgOwner == "")
                checkEditText(ownerPic)

            if (imgs.size == 0)
                checkEditText(productPics)

            if (productDescr.error == null
                && productWilaya.error == null
                && productName.error == null
                && ownerEmail.error == null
                && ownerName.error == null
                && ownerPhone.error == null
                && ownerPic.error == null
                && productPics.error == null
            ) {

                if (imgOwner != "")
                    ownerObject =
                        Owner(
                            ownerName.text.toString(),
                            imgOwner,
                            ownerEmail.text.toString(),
                            ownerPhone.text.toString()
                        )
                else if (ownerPic.text != null)
                    ownerObject = Owner(
                        ownerName.text.toString(),
                        ownerPic.text.toString(),
                        ownerEmail.text.toString(),
                        ownerPhone.text.toString()
                    )
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                if (imgs.size != 0) {
                    productObject =
                        Product(
                            ownerObject,
                            imgs,
                            productName.text.toString(),
                            productDescr.text.toString(),
                            wilaya.text.toString(),
                            currentDate
                        )
                } else if (ownerPic.text != null)
                    productObject = Product(
                        ownerObject,
                        productPics.text.toString().split(","),
                        productName.text.toString(),
                        productDescr.text.toString(),
                        wilaya.text.toString(),
                        currentDate
                    )

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("newProduct", productObject)
                startActivity(intent)
            }
        }

    }

    private fun checkEditText(editText: EditText) {
        if (editText.text.toString().isEmpty())
            editText.error = "This should contain something"
    }


    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                intent,
                REQUEST_SELECT_IMAGE_IN_ALBUM
            )
        }
    }

    private fun selectImagesInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_SELECT_IMAGES_IN_ALBUM
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SELECT_IMAGES_IN_ALBUM || requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // something is wrong
                }

                val clipData = data?.clipData
                if (clipData != null) { // handle multiple photo
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri

                        importPhoto(uri, requestCode)
                    }
                } else { // handle single photo
                    val uri = data?.data
                    if (uri != null) {
                        importPhoto(uri, requestCode)
                    }
                }
            }
        }
    }

    private fun isImage(context: Context, uri: Uri): Boolean {
        val mimeType = context.contentResolver.getType(uri) ?: return true
        return mimeType.startsWith("image/")
    }


    private fun importPhoto(uri: Uri, requestCode: Int): Boolean {
        if (!isImage(this, uri)) {
            // not image
            return false
        }

        if (requestCode == REQUEST_SELECT_IMAGES_IN_ALBUM) {
            imgs.add(uri.toString())
            productPics.isEnabled = false
            productPics.error = null
        } else {
            if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
                imgOwner = uri.toString()
                ownerPic.isEnabled = false
                ownerPic.error = null
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private const val REQUEST_SELECT_IMAGES_IN_ALBUM = 2
    }
}
