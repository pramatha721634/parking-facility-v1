package com.parking.facility.rest

interface ParkingFacilityService {
    fun getParkingFacilityDetails(city : String): String?
}