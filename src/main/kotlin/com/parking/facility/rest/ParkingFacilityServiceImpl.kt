package com.parking.facility.rest

import com.charging.stations.response.Address
import com.charging.stations.response.Category
import com.charging.stations.response.ChargingStationsResponse
import com.charging.stations.response.ItemDetails
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.parking.facility.configuration.ParkFacilityConfig
import com.parking.facility.exception.CityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Service
class ParkingFacilityServiceImpl : ParkingFacilityService {

    @Autowired
    lateinit var parkFacilityConfig: ParkFacilityConfig

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var objectMapper: ObjectMapper

    var itemDetailsList : HashSet<ItemDetails> = HashSet<ItemDetails>()


    override fun getParkingFacilityDetails(city : String): ChargingStationsResponse? {

        var longitudeAndLatitude = getLongitudeAndLatitude(city)

        if (longitudeAndLatitude == null)  {
            throw CityNotFoundException("Given City not found!!!!")
        }
        val baseURL: String = parkFacilityConfig.baseUrl + "?at=" + longitudeAndLatitude + "&cat=" + parkFacilityConfig.cat + "&apiKey="+parkFacilityConfig.apiKey

        var parkingFacilityResponse = restTemplate.getForObject(baseURL, String::class.java)
        return getparkingFacilityResponse(parkingFacilityResponse)
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

    fun getparkingFacilityResponse(chargingStationsDetails : String?) : ChargingStationsResponse {
        var chargingStationsResponse = ChargingStationsResponse()

        try {
            var jsonNode: JsonNode = objectMapper.readTree(chargingStationsDetails)

            var itemDetailsList : Set<ItemDetails> = getItemDetailsList(jsonNode)
            var address : Address = getAddress(jsonNode)

            chargingStationsResponse.address = address
            chargingStationsResponse.parkingFacilityList = itemDetailsList

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return chargingStationsResponse
    }

    fun getItemDetailsList(jsonNode: JsonNode) : Set<ItemDetails> {

        var JsonNodeResult = jsonNode.get("results")
        var jsonNodeResultList = JsonNodeResult.get("items")

        for (jsonResultNode in jsonNodeResultList){

            var itemDetails : ItemDetails = getItemDetails(jsonResultNode)
            itemDetailsList.add(itemDetails)

            if(itemDetailsList.size == 3) {
                break
            }
        }
        return itemDetailsList
    }

    fun getItemDetails(jsonResultNode: JsonNode) : ItemDetails {

        var itemDetails = ItemDetails();
        itemDetails.id = jsonResultNode.get("id").asText()
        itemDetails.distance = jsonResultNode.get("distance").asText()
        itemDetails.title = jsonResultNode.get("title").asText()
        itemDetails.averageRating = jsonResultNode.get("averageRating").asText()
        itemDetails.vicinity = jsonResultNode.get("vicinity").asText()
        itemDetails.type = jsonResultNode.get("type").asText()
        itemDetails.category = getCategory(jsonResultNode)

        return itemDetails
    }

    fun getCategory (jsonNode: JsonNode) : Category {
        var category = Category()
        var jsonNodeCategory = jsonNode.get("category")
        category.id = jsonNodeCategory.get("id").asText()
        category.title = jsonNodeCategory.get("title").asText()
        category.type = jsonNodeCategory.get("type").asText()
        category.system = jsonNodeCategory.get("system").asText()

        return category
    }

    fun getAddress(jsonNode: JsonNode) : Address {
        var address  = Address();
        var jsonNodeSearch = jsonNode.get("search")
        var jsonNodeContext = jsonNodeSearch.get("context")
        var jsonNodeLocation = jsonNodeContext.get("location")

        var jsonNodeAddress = jsonNodeLocation.get("address")
        address.houseNo     = jsonNodeAddress.get("house").asText()
        address.street      = jsonNodeAddress.get("street").asText()
        address.postalCode  = jsonNodeAddress.get("postalCode").asText()
        address.district    = jsonNodeAddress.get("district").asText()
        address.city        = jsonNodeAddress.get("city").asText()
        address.county      = jsonNodeAddress.get("county").asText()
        address.stateCode   = jsonNodeAddress.get("stateCode").asText()
        address.country     = jsonNodeAddress.get("country").asText()
        address.countryCode = jsonNodeAddress.get("countryCode").asText()

        return address
    }

}