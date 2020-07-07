package com.parking.facility.controller

import com.parking.facility.rest.ParkingFacilityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ParkingFacilityController {

    @Autowired
    lateinit var parkingFacilityService: ParkingFacilityService

    @GetMapping("/parking_facility/{city}")
    fun getParkingFacilityDetails(@PathVariable city: String): String? {
        return parkingFacilityService.getParkingFacilityDetails(city)
    }
}