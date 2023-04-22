package com.example.a4350individualassign1

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle

class ProfileDisplayActivity : AppCompatActivity() {

    private var nameReceived: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)

        //Get the intent that created this activity.
        val receivedIntent = intent

        //Get the string data
        nameReceived = receivedIntent.getStringExtra("NAME")

        // Display received data in text views
        findViewById<TextView>(R.id.logged_in_string)!!.text = nameReceived!!.toString() + " is logged in!"
    }
}
