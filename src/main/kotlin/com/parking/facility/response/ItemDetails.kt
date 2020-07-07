package com.charging.stations.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class ItemDetails {

    lateinit var id             : String
    lateinit var distance       : String
    lateinit var title          : String
    lateinit var averageRating  : String
    lateinit var vicinity       : String
    lateinit var type           : String
    lateinit var category       : Category


    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemDetails

        if (id != other.id) return false
        if (distance != other.distance) return false
        if (title != other.title) return false
        if (averageRating != other.averageRating) return false
        if (vicinity != other.vicinity) return false
        if (type != other.type) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + averageRating.hashCode()
        result = 31 * result + vicinity.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}