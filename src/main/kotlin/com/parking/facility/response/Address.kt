package com.charging.stations.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class Address {

    lateinit var houseNo      : String
    lateinit var street       : String
    lateinit var postalCode   : String
    lateinit var district     : String
    lateinit var city         : String
    lateinit var county       : String
    lateinit var stateCode    : String
    lateinit var country      : String
    lateinit var countryCode  : String

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (houseNo != other.houseNo) return false
        if (street != other.street) return false
        if (postalCode != other.postalCode) return false
        if (district != other.district) return false
        if (city != other.city) return false
        if (county != other.county) return false
        if (stateCode != other.stateCode) return false
        if (country != other.country) return false
        if (countryCode != other.countryCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = houseNo.hashCode()
        result = 31 * result + street.hashCode()
        result = 31 * result + postalCode.hashCode()
        result = 31 * result + district.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + county.hashCode()
        result = 31 * result + stateCode.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + countryCode.hashCode()
        return result
    }
}