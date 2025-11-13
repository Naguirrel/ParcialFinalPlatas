package com.naguirrel.uvgaguirrenorman

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.room.Room
import com.naguirrel.uvgaguirrenorman.data.local.AppDatabase
import com.naguirrel.uvgaguirrenorman.data.local.datastore.SettingsRepository
import com.naguirrel.uvgaguirrenorman.data.remote.CoinCapApiImpl
import com.naguirrel.uvgaguirrenorman.data.repository.AssetRepositoryImpl
import com.naguirrel.uvgaguirrenorman.presentation.navigation.AppNavHost

@Composable
fun CryptoTrackerApp(context: Context) {
    val repo = remember {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "crypto_db"
        ).build()
        val api = CoinCapApiImpl()
        val settings = SettingsRepository(context)
        AssetRepositoryImpl(api, db.assetDao(), settings)
    }

    MaterialTheme {
        Surface {
            AppNavHost(repo = repo)
        }
    }
}
