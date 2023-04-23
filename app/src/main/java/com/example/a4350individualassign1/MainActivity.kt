package com.example.a4350individualassign1

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.navigation.NavigationBarView
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mButtonCamera: Button? = null
    private var mButtonSubmit: Button? = null
    private var mIvPic: ImageView? = null
    private var firstNameTextEdit: EditText? = null
    private var lastNameTextEdit: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        firstNameTextEdit = findViewById(R.id.firstNameInput)
        lastNameTextEdit = findViewById(R.id.lastNameInput)

        //Get the buttons
        mButtonCamera = findViewById(R.id.PhotoButton)
        mButtonSubmit = findViewById(R.id.button_submit)

        //Specify that this class has the listener in it
        mButtonCamera!!.setOnClickListener(this)
        mButtonSubmit!!.setOnClickListener(this)

        var bits: Bitmap? = getBitmapFromCache() //redraw saved profile image
        if (bits != null) {
            mIvPic = findViewById<View>(R.id.iv_pic) as ImageView
            mIvPic!!.setImageBitmap(bits)
        }
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.PhotoButton -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                }
            }
            R.id.button_submit -> {
                val firstNameTextEdit: EditText? = findViewById(R.id.firstNameInput)
                val firstNameValue: String = firstNameTextEdit!!.text.toString()
                val middleNameTextEdit: EditText? = findViewById(R.id.middleNameInput)
                val middleNameValue: String = middleNameTextEdit!!.text.toString()
                val lastNameTextEdit: EditText? = findViewById(R.id.lastNameInput)
                val lastNameValue: String = lastNameTextEdit!!.text.toString()

                //Starts new activity and passes data into it
                val messageIntent = Intent(this, ProfileDisplayActivity::class.java)
                messageIntent.putExtra("NAME", "$firstNameValue $lastNameValue")
                this.startActivity(messageIntent)
            }
        }
    }


    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            mIvPic = findViewById<View>(R.id.iv_pic) as ImageView

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                mIvPic!!.setImageBitmap(thumbnailImage)
                if (thumbnailImage != null) {
                    saveBitmapToCache(thumbnailImage) //save image to cache
                }
            }
            else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                mIvPic!!.setImageBitmap(thumbnailImage)

                if (thumbnailImage != null) { // save image to cache
                    saveBitmapToCache(thumbnailImage)
                }
            }
        }
    }
    /**
     * Saves a photo to the cache.
     * Used in [cameraActivity]
     */
    private fun saveBitmapToCache(bitmap: Bitmap) {
        val fileName = "photo.png"
        val file = File(cacheDir, fileName)
        val outputStream = FileOutputStream(file)

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream.close()
        }
    }

    /**
     * Retrieves the profile photo from the cache.
     * Used in [onCreate]
     */
    private fun getBitmapFromCache(): Bitmap? {
        val fileName = "photo.png"
        val file = File(cacheDir, fileName)

        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}