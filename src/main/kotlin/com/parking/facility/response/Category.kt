package com.charging.stations.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class Category {

    lateinit var id        : String
    lateinit var title     : String
    lateinit var type      : String
    lateinit var system    : String

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (title != other.title) return false
        if (type != other.type) return false
        if (system != other.system) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + system.hashCode()
        return result
    }

    override fun toString(): String {
        return "Category(id='$id', title='$title', type='$type', system='$system')"
    }

}