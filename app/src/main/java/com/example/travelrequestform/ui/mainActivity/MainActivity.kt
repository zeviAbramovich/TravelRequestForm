package com.example.travelrequestform.ui.mainActivity

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.travelrequestform.R
import com.example.travelrequestform.ui.addTravelActivity.AddTravelActivity

// The opening page of the App
// contain welcome text and button for move to the from page
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Sends to the travel request page
    fun openFormClick(view: View) {
        val i = Intent(this, AddTravelActivity::class.java)
        startActivity(i)
    }
}