package com.naguirrel.uvgaguirrenorman.data.mapper

import com.naguirrel.uvgaguirrenorman.data.local.entity.AssetEntity
import com.naguirrel.uvgaguirrenorman.data.remote.dto.AssetDto
import com.naguirrel.uvgaguirrenorman.domain.model.Asset

fun AssetDto.toEntity() = AssetEntity(
    id = id,
    name = name,
    symbol = symbol,
    priceUsd = priceUsd?.toDoubleOrNull(),
    changePercent24Hr = changePercent24Hr?.toDoubleOrNull(),
    supply = supply?.toDoubleOrNull(),
    maxSupply = maxSupply?.toDoubleOrNull(),
    marketCapUsd = marketCapUsd?.toDoubleOrNull()
)

fun AssetEntity.toDomain() = Asset(
    id = id,
    name = name,
    symbol = symbol,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
    supply = supply,
    maxSupply = maxSupply,
    marketCapUsd = marketCapUsd
)
