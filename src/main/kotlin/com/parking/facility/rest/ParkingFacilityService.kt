package com.parking.facility.rest

import com.charging.stations.response.ChargingStationsResponse

interface ParkingFacilityService {
    fun getParkingFacilityDetails(city : String): ChargingStationsResponse?
}