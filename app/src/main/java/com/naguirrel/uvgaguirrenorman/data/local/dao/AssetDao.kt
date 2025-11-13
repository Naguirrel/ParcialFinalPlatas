package com.naguirrel.uvgaguirrenorman.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naguirrel.uvgaguirrenorman.data.local.entity.AssetEntity

@Dao
interface AssetDao {

    @Query("SELECT * FROM assets ORDER BY name ASC")
    suspend fun getAllAssets(): List<AssetEntity>

    @Query("SELECT * FROM assets WHERE id = :id LIMIT 1")
    suspend fun getAssetById(id: String): AssetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(assets: List<AssetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: AssetEntity)

    @Query("DELETE FROM assets")
    suspend fun clearAll()
}
