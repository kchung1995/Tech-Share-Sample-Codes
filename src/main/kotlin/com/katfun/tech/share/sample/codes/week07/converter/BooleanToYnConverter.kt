package com.katfun.tech.share.sample.codes.week07.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class BooleanToYnConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean): String = when(attribute) {
        true -> "Y"
        false -> "N"
    }

    override fun convertToEntityAttribute(dbData: String): Boolean = when(dbData) {
        "Y" -> true
        else -> false
    }
}