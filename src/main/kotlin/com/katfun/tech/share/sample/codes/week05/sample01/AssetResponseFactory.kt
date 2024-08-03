package com.katfun.tech.share.sample.codes.week05.sample01

object AssetResponseFactory {
    fun toResponseDto(
        asset: Asset
    ): AssetResponse {
        if (asset is BankAccount) require(asset.isShared.not())
        return AssetResponse(
            id = UserId(id = asset.id)
        )
    }
}

data class AssetResponse(
    val id: UserId
)