package com.naguirrel.uvgaguirrenorman.data.repository

import com.naguirrel.uvgaguirrenorman.data.local.dao.AssetDao
import com.naguirrel.uvgaguirrenorman.data.local.datastore.SettingsRepository
import com.naguirrel.uvgaguirrenorman.data.mapper.toDomain
import com.naguirrel.uvgaguirrenorman.data.mapper.toEntity
import com.naguirrel.uvgaguirrenorman.data.remote.CoinCapApi
import com.naguirrel.uvgaguirrenorman.domain.model.Asset
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetRepository
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class AssetRepositoryImpl(
    private val api: CoinCapApi,
    private val dao: AssetDao,
    private val settings: SettingsRepository
) : AssetRepository {

    override suspend fun getAssets(): AssetResult<List<Asset>> = withContext(Dispatchers.IO) {
        try {
            val remote = api.getAssets()
            val entities = remote.map { it.toEntity() }
            AssetResult(
                data = entities.map { it.toDomain() },
                fromLocal = false,
                lastUpdate = null
            )
        } catch (e: Exception) {
            val local = dao.getAllAssets()
            if (local.isNotEmpty()) {
                val last = settings.lastUpdateFlow.firstOrNull()
                AssetResult(
                    data = local.map { it.toDomain() },
                    fromLocal = true,
                    lastUpdate = last
                )
            } else {
                AssetResult(
                    data = null,
                    fromLocal = true,
                    lastUpdate = null
                )
            }
        }
    }

    override suspend fun getAssetById(id: String): AssetResult<Asset> = withContext(Dispatchers.IO) {
        try {
            val remote = api.getAssetById(id)
            val entity = remote.toEntity()
            AssetResult(
                data = entity.toDomain(),
                fromLocal = false,
                lastUpdate = null
            )
        } catch (e: Exception) {
            val local = dao.getAssetById(id)
            if (local != null) {
                val last = settings.lastUpdateFlow.firstOrNull()
                AssetResult(
                    data = local.toDomain(),
                    fromLocal = true,
                    lastUpdate = last
                )
            } else {
                AssetResult(
                    data = null,
                    fromLocal = true,
                    lastUpdate = null
                )
            }
        }
    }

    override suspend fun saveOfflineData(): Result<Unit> = runCatching {
        val remote = api.getAssets()
        val entities = remote.map { it.toEntity() }
        dao.clearAll()
        dao.insertAll(entities)
        settings.setLastUpdate(System.currentTimeMillis())
    }
}
