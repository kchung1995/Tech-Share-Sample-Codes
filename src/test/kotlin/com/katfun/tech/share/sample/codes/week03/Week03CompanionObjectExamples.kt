package com.katfun.tech.share.sample.codes.week03

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.`in`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class Week03CompanionObjectExamples {
}

object EntityConverter {
    fun requestToEntity(request: SomeRequest): SomeEntity {
        with(request) {
            return SomeEntity(
                id = id
            )
        }
    }

    const val ERROR_MESSAGE = "해당하는 값을 찾을 수 없습니다."
}

data class SomeRequest(
    val id: Long
)

// @Entity... 가 붙어 있다고 가정합시다
class SomeEntity(
    val id: Long? = null
)

// Companion Object

class Birthday private constructor(
    val birthday: LocalDate
) {
    companion object {
        private const val BIRTHDAY_INVALID = "생일 날짜가 잘못되었습니다."

        // factory method
        fun of(input: LocalDate, now: LocalDate = LocalDate.now()): Birthday {
            require(input <= now) { BIRTHDAY_INVALID }
            return Birthday(input)
        }
    }
}

internal class CompanionObjectTest {
    @Test
    fun birthdayExample() {
        val birthdayTest = LocalDate.of(2022, 12, 19)
        val birthdayInstance = Birthday.of(birthdayTest)
        println(birthdayInstance)

        val invalidBirthday = LocalDate.of(2025, 12, 19)
//        val primaryConstructorIsHidden = Birthday(invalidBirthday)
        val invalidException = assertThrows<IllegalArgumentException> { Birthday.of(invalidBirthday) }
        assertThat(invalidException.message).isEqualTo("생일 날짜가 잘못되었습니다.")

        println("first one: ${birthdayInstance.birthday}")
    }
}