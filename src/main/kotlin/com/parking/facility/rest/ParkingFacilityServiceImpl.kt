package com.parking.facility.rest

import com.parking.facility.configuration.ParkFacilityConfig
import com.parking.facility.exception.CityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ParkingFacilityServiceImpl : ParkingFacilityService {

    @Autowired
    lateinit var parkFacilityConfig: ParkFacilityConfig

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun getParkingFacilityDetails(city : String): String? {

        var longitudeAndLatitude = getLongitudeAndLatitude(city)
        println("${city} ====== ${longitudeAndLatitude}")

        if (longitudeAndLatitude == null)  {
            throw CityNotFoundException("Given City not found!!!!")
        }
        val baseURL: String = parkFacilityConfig.baseUrl + "?at=" + longitudeAndLatitude + "&cat=" + parkFacilityConfig.cat + "&apiKey="+parkFacilityConfig.apiKey

        var parkingFacilityResponse = restTemplate.getForObject(baseURL, String::class.java)
        return parkingFacilityResponse
    }

    fun getLongitudeAndLatitude(city : String) : String? {

        var longitudeAndLatitudeMap:HashMap<String, String> = HashMap<String, String>()

        longitudeAndLatitudeMap.put("Madrid", "40.4168,3.7038");
        longitudeAndLatitudeMap.put("Budapest", "47.4979,19.0402");
        longitudeAndLatitudeMap.put("Prague", "50.0755,14.4378");
        longitudeAndLatitudeMap.put("Barcelona", "41.3851,2.1734");
        longitudeAndLatitudeMap.put("Stockholm", "59.3293,18.0686");
        longitudeAndLatitudeMap.put("Rome", "41.9028,12.4964");
        longitudeAndLatitudeMap.put("Paris", "48.8566,2.3522");
        longitudeAndLatitudeMap.put("London", "51.5074,0.1278");
        longitudeAndLatitudeMap.put("Amsterdam", "52.3667,4.8945");
        longitudeAndLatitudeMap.put("Vienna", "48.2082,16.3738");

        for(key in longitudeAndLatitudeMap.keys) {
            if (key.equals(city, true) ) {
                return  longitudeAndLatitudeMap[key]
            }
        }
        return null
    }
}