package com.example.travelrequestform.data.models

import android.annotation.SuppressLint
import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.android.libraries.places.api.model.Place
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// Represent travel details
// Can be stored in FireBase and/or in MySql through ROOM
@Entity
class Travel {

    @PrimaryKey
    var travelId: String? = null
    var clientName: String? = null
    var clientPhone: String? = null
    var clientEmail: String? = null
    var companyEmail: String? = null
    var travelDate: String? = null
    var arrivalDate: String? = null
    var numOfTravelers: Int? = null

    @TypeConverters(UserLocationConverter::class)
    var address: UserLocation? = null

    @TypeConverters(UserLocationConverter::class)
    var travelLocations: MutableList<UserLocation> = arrayListOf()

    @TypeConverters(RequestType::class)
    var requestType: RequestType? = null

    @TypeConverters(CompanyConverter::class)
    var company: HashMap<String, Boolean> = HashMap()

    enum class RequestType(val code: Int) {
        SENT(0), ACCEPTED(1), RUN(2), CLOSE(3), PAYMENT(4);

        companion object {
            @TypeConverter
            fun getType(numeral: Int): RequestType? {
                for (ds in values()) if (ds.code == numeral) return ds
                return null
            }

            @TypeConverter
            fun getTypeInt(requestType: RequestType?): Int? {
                return requestType?.code
            }
        }
    }

    class CompanyConverter {
        @TypeConverter
        fun fromString(value: String?): HashMap<String, Boolean>? {
            if (value == null || value.isEmpty()) return null
            val mapString =
                value.split(",").toTypedArray() //split map into array of (string,boolean) strings
            val hashMap = HashMap<String, Boolean>()
            for (s1 in mapString)  //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) { //is empty maybe will needed because the last char in the string is ","
                    val s2 = s1.split(":")
                        .toTypedArray() //split (string,boolean) to company string and boolean string.
                    val aBoolean = java.lang.Boolean.parseBoolean(s2[1])
                    hashMap[s2[0]] = aBoolean
                }
            }
            return hashMap
        }

        @TypeConverter
        fun asString(map: HashMap<String?, Boolean?>?): String? {
            if (map == null) return null
            val mapString = StringBuilder()
            for ((key, value) in map) mapString.append(
                key
            ).append(":").append(value).append(",")
            return mapString.toString()
        }
    }

    class UserLocationConverter {
        @TypeConverter
        fun fromString(value: String?): UserLocation? {
            if (value == null || value == "") return null
            val lat = value.split(" ").toTypedArray()[0].toDouble()
            val long = value.split(" ").toTypedArray()[1].toDouble()
            return UserLocation(lat, long)
        }

        @TypeConverter
        fun asString(warehouseUserLocation: UserLocation?): String {
            return if (warehouseUserLocation == null) "" else warehouseUserLocation.getLon()
                .toString() + " " + warehouseUserLocation.getLat()
        }
    }

    class UserLocation() {
        private var lat: Double? = null
        private var lon: Double? = null
        fun getLat(): Double? {
            return lat
        }

        fun getLon(): Double? {
            return lon
        }

        constructor(lat: Double?, lon: Double?) : this() {
            this.lat = lat
            this.lon = lon
        }

        constructor(place: Place) : this() {
            this.lat = place.latLng?.latitude
            this.lon = place.latLng?.longitude
        }
    }
}