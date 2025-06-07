package com.katfun.tech.share.sample.codes.week07

import com.katfun.tech.share.sample.codes.week07.configuration.KeyIv
import com.katfun.tech.share.sample.codes.week07.converter.BooleanToYnConverter
import com.katfun.tech.share.sample.codes.week07.converter.StringEncryptConverter
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class Week07SampleJdbcRepository(
    private val jdbc: NamedParameterJdbcTemplate,
    private val databaseKeyIv: KeyIv
) {
    companion object {
        const val INSERT_SQL = """
            insert into week07_sample_table_name
            (study_yn, phone_number)
            values
            (:isStudy, :phoneNumber)
        """
    }

    fun saveAll(entities: List<Week07SampleEntity>) {
        jdbc.batchUpdate(
            INSERT_SQL.trimIndent(),
            entities
                .map { it.toInsertMap() }
                .toTypedArray()
        )
    }

    private fun Week07SampleEntity.toInsertMap(): Map<String, Any?> {
        val booleanToYnConverter = BooleanToYnConverter()
        val stringEncryptConverter = StringEncryptConverter(databaseKeyIv)

        return mapOf(
            "isStudy" to booleanToYnConverter.convertToDatabaseColumn(isStudy),
            "phoneNumber" to stringEncryptConverter.convertToDatabaseColumn(phoneNumber)
        )
    }
}