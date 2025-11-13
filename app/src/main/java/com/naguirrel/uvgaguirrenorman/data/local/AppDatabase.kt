package com.naguirrel.uvgaguirrenorman.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naguirrel.uvgaguirrenorman.data.local.dao.AssetDao
import com.naguirrel.uvgaguirrenorman.data.local.entity.AssetEntity

@Database(entities = [AssetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
}
