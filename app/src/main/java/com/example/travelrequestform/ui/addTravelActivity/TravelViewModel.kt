package com.example.travelrequestform.ui.addTravelActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelrequestform.data.models.Travel
import com.example.travelrequestform.data.repositories.TravelRepository

// Represent the View Model of AddTravelActivity
class TravelViewModel : ViewModel() {

    private val travelRepo = TravelRepository()

    // Add travel obj to the DataBase
    fun addTravel(travel: Travel) {
        travelRepo.addTravel(travel)
    }

    // Get the boolean value Which indicates whether the value
    // was successfully inserted into the database
    fun getIsSuccess(): LiveData<Boolean> {
        return travelRepo.getIsSuccess()
    }
}