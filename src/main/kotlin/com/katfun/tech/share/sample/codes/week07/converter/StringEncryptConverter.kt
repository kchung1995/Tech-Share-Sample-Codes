package com.katfun.tech.share.sample.codes.week07.converter

import com.katfun.tech.share.sample.codes.week07.configuration.KeyIv
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringEncryptConverter(
    databaseKeyIv: KeyIv
) : AttributeConverter<String, String> {
    private val key = databaseKeyIv.key
    private val iv = databaseKeyIv.iv

    override fun convertToDatabaseColumn(attribute: String?): String? =
        attribute?.let { AESCipherManager.encrypt(attribute, key, iv) }

    override fun convertToEntityAttribute(dbData: String?): String? =
        dbData?.let { AESCipherManager.decrypt(dbData, key, iv) }
}

object AESCipherManager {
    fun encrypt(plainText: String, secretKey: String, iv: String) = plainText
    fun decrypt(encryptedText: String, secretKey: String, iv: String) = encryptedText
}