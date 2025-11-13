package com.naguirrel.uvgaguirrenorman.domain.repository

import com.naguirrel.uvgaguirrenorman.domain.model.Asset

data class AssetResult<T>(
    val data: T?,
    val fromLocal: Boolean,
    val lastUpdate: Long?
)

interface AssetRepository {
    suspend fun getAssets(): AssetResult<List<Asset>>
    suspend fun getAssetById(id: String): AssetResult<Asset>
    suspend fun saveOfflineData(): Result<Unit>
}
