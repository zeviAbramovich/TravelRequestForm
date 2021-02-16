package com.example.travelrequestform.data.repositories

import androidx.lifecycle.LiveData
import com.example.travelrequestform.data.models.Travel
// Represent the repository of the travel. Can use several sources of databases
class TravelRepository {

    private val travelDataSource = TravelDataSource.instance as TravelDataSource

    fun addTravel(travel: Travel){
        travelDataSource.addTravel(travel)
    }

    // Get the boolean value Which indicates whether the value
    // was successfully inserted into the database
    fun getIsSuccess() : LiveData<Boolean> {
        return travelDataSource.isSuccess
    }
}