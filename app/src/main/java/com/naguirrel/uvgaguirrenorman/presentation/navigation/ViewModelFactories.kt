package com.naguirrel.uvgaguirrenorman.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetRepository
import com.naguirrel.uvgaguirrenorman.presentation.assets.AssetsViewModel
import com.naguirrel.uvgaguirrenorman.presentation.detail.AssetDetailViewModel

class AssetsViewModelFactory(
    private val repo: AssetRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetsViewModel::class.java)) {
            return AssetsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AssetDetailViewModelFactory(
    private val repo: AssetRepository,
    private val assetId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetDetailViewModel::class.java)) {
            return AssetDetailViewModel(repo, assetId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
