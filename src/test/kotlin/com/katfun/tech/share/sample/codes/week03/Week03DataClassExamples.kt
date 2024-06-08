package com.katfun.tech.share.sample.codes.week03

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Week03DataClassExamples {
    private val katfun = User(
        id = 1L,
        name = "정규호",
        age = 20,
        address = Address(
            country = "Korea, Republic Of",
            city = "Yongin-si, Gyeonggi-do",
            street = "Katfun-ro 6",
            detail = "Bldg 1557, Room 88848",
            postalNumber = 12345
        )
    )

    // katfun과 내용이 완전히 똑같음
    private val katfunAnother = User(
        id = 1L,
        name = "정규호",
        age = 20,
        address = Address(
            country = "Korea, Republic Of",
            city = "Yongin-si, Gyeonggi-do",
            street = "Katfun-ro 6",
            detail = "Bldg 1557, Room 88848",
            postalNumber = 12345
        )
    )

    @Test
    fun `equals Test`() {
        assertThat(katfun).isEqualTo(katfunAnother)
        // if you still cannot trust the result,
        if (katfun == katfunAnother) {
            println("They are equal.")
        } else {
            println("They are NOT equal.")
        }
    }

    @Test
    fun `hashcode test`() {
        val firstHashcode = katfun.hashCode()
        val secondHashcode = katfunAnother.hashCode()

        println(firstHashcode)
        println(secondHashcode)
        assertThat(firstHashcode).isEqualTo(secondHashcode)
    }

    @Test
    fun `toString example`() {
        val toStringResult = katfun.toString()
        println(toStringResult)
    }

    @Test
    fun `componentN example`() {
        val firstOne = katfun.component1()
        assertThat(firstOne).isEqualTo(katfun.id)
        println(firstOne)

        val deeperOne = katfun.component4().component3()
        assertThat(deeperOne).isEqualTo(katfun.address.street)
        println(deeperOne)
    }

    @Test
    fun `equality and identity`() {
        val katfun = katfun
        val katfunAnother = katfunAnother

        assertThat(katfun).isEqualTo(katfunAnother)
        assertThat(katfun !== katfunAnother).isTrue
    }

    @Test
    fun `additional - normal class`() {
        val first = Hello("katfun")
        val second = Hello("bruceHan")

        assertThat(first).isNotEqualTo(second)
        // why? their hashcode is different
        println("katfun: ${first.hashCode()}")
        println("bruceHan: ${second.hashCode()}")
    }
}

internal data class User(
    val id: Long,
    val name: String,
    val age: Int,
    val address: Address
)

internal data class Address(
    val country: String,
    val city: String,
    val street: String,
    val detail: String,
    val postalNumber: Int
)

internal class Hello(
    val name: String
)