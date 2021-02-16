package com.example.travelrequestform.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.travelrequestform.data.models.Travel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

// Represent and contain one instance of the FireBase DB
class TravelDataSource private constructor() {

    // Indicates whether the travel was successfully inserted into the database
    val isSuccess = MutableLiveData<Boolean>()

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val travelsRef: DatabaseReference = firebaseDatabase.getReference("ExistingTravels")
    private val isTravelAdded: DatabaseReference = firebaseDatabase.getReference("isTravelAdded")

    // Add travel obj to the DataBase
    fun addTravel(travel: Travel) {
        val id: String = travelsRef.push().key as String
        travel.travelId = id
        val task = travelsRef.child(id).setValue(travel)

        task.addOnCompleteListener(object : OnCompleteListener<Void> {

            override fun onComplete(p0: Task<Void>) {
                isSuccess.value = task.isSuccessful

                if (task.isSuccessful) {
                    isTravelAdded.setValue(true)
                }
            }
        })
    }

    // Singleton
    companion object {
        var instance: TravelDataSource? = null
            get() {
                if (field == null) field = TravelDataSource()
                return field
            }
            private set
    }
}
