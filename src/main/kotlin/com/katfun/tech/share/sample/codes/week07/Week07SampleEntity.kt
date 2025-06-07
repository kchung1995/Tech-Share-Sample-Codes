package com.katfun.tech.share.sample.codes.week07

import com.katfun.tech.share.sample.codes.week07.converter.BooleanToYnConverter
import com.katfun.tech.share.sample.codes.week07.converter.StringEncryptConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.Comment

@Entity
class Week07SampleEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,

    @Comment("Boolean 대상")
    @Convert(converter = BooleanToYnConverter::class)
    val isStudy: Boolean,

    @Comment("암호화 대상")
    @Convert(converter = StringEncryptConverter::class)
    val phoneNumber: String
)