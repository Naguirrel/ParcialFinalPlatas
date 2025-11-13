package com.naguirrel.uvgaguirrenorman.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AssetListResponseDto(
    val data: List<AssetDto>
)

@Serializable
data class AssetSingleResponseDto(
    val data: AssetDto
)

@Serializable
data class AssetDto(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String? = null,
    val maxSupply: String? = null,
    val marketCapUsd: String? = null,
    val priceUsd: String? = null,
    val changePercent24Hr: String? = null
)
