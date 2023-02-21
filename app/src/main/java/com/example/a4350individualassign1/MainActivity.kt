package com.example.a4350individualassign1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mButtonCamera: Button? = null
    private var mButtonSubmit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get the buttons
        mButtonCamera = findViewById(R.id.PhotoButton)
        mButtonSubmit = findViewById(R.id.button_submit)

        //Say that this class itself contains the listener
        mButtonCamera!!.setOnClickListener(this)
        mButtonSubmit!!.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when (view.id) { //Added ? due to warning message. Consider better checks.
/*            R.id.PhotoButton -> {
                //The button press should open a camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                    //Do error handling here
                }
            }*/
            R.id.button_submit -> {
                val firstNameTextEdit: EditText? = findViewById(R.id.firstNameInput)
                val firstNameValue: String = firstNameTextEdit!!.text.toString()
                val middleNameTextEdit: EditText? = findViewById(R.id.middleNameInput)
                val middleNameValue: String = middleNameTextEdit!!.text.toString()
                val lastNameTextEdit: EditText? = findViewById(R.id.lastNameInput)
                val lastNameValue: String = lastNameTextEdit!!.text.toString()

                //Start an activity and pass the data to it.
                val messageIntent = Intent(this, ProfileDisplayActivity::class.java)
                messageIntent.putExtra("NAME", "$firstNameValue $middleNameValue $lastNameValue")
                this.startActivity(messageIntent)
            }
        }
    }


    /*private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            mIvPic = findViewById<View>(R.id.iv_pic) as ImageView
            //val extras = result.data!!.extras
            //val thumbnailImage = extras!!["data"] as Bitmap?

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                mIvPic!!.setImageBitmap(thumbnailImage)
                if (thumbnailImage != null) { *//** This saves the thumbnail to cache **//*
                    saveBitmapToCache(thumbnailImage)
                }
            }
            else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                mIvPic!!.setImageBitmap(thumbnailImage)

                if (thumbnailImage != null) { *//** This saves the thumbnail to cache **//*
                    saveBitmapToCache(thumbnailImage)
                }
            }
        }
    }*/
}