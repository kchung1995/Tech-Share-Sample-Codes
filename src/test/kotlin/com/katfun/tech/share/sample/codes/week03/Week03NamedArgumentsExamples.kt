package com.katfun.tech.share.sample.codes.week03

import org.junit.jupiter.api.Test

class Week03NamedArgumentsExamples {
    @Test
    fun `userInfo with and without named arguments`() {
        val userWithNamedArguments = UserInfo(
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

        val userWithoutNamedArguments = UserInfo(
            1L,
            "정규호",
            20,
            Address(
                "Korea, Republic Of",
                "Yongin-si, Gyeonggi-do",
                "Katfun-ro 6",
                "Bldg 1557, Room 88848",
                2345
            )
        )
    }

    @Test
    fun `lets see how things can get tough without named arguments`() {
        val thisIsWhateverExample = UserInfo(
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

        // and here it comes
        thisIsWhateverExample.thisIsAStupidExample(true, false, false, true, false, false, true)

        // would have been better if
        thisIsWhateverExample.thisIsAStupidExample(
            hasId = true,
            hasName = false,
            hasAge = false,
            hasAddress = true,
            hasIdAndName = false,
            hasIdAndAge = false,
            hasIdAndAddress = true
        )

        // this is also possible
        thisIsWhateverExample.thisIsAStupidExample(
            hasId = true,
            hasAddress = true,
            hasName = false,
            hasAge = false,
            hasIdAndAddress = true,
            hasIdAndName = false,
            hasIdAndAge = false
        )
    }
}

internal data class UserInfo(
    val id: Long,
    val name: String,
    val age: Int,
    val address: Address
) {
    fun thisIsAStupidExample(
        hasId: Boolean,
        hasName: Boolean,
        hasAge: Boolean,
        hasAddress: Boolean,
        hasIdAndName: Boolean,
        hasIdAndAge: Boolean,
        hasIdAndAddress: Boolean
    ) {
        // here comes some implementations
    }
}
