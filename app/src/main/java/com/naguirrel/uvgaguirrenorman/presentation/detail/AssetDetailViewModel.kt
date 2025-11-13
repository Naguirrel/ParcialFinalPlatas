package com.naguirrel.uvgaguirrenorman.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naguirrel.uvgaguirrenorman.domain.model.Asset
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetRepository
import com.naguirrel.uvgaguirrenorman.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssetDetailViewModel(
    private val repo: AssetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val assetId: String = checkNotNull(savedStateHandle["assetId"])

    private val _uiState = MutableStateFlow<UiState<Asset>>(UiState.Loading)
    val uiState: StateFlow<UiState<Asset>> = _uiState

    init {
        loadAsset()
    }

    fun loadAsset() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repo.getAssetById(assetId)
            val data = result.data
            if (data != null) {
                _uiState.value = UiState.Success(
                    data = data,
                    fromLocal = result.fromLocal,
                    lastUpdate = result.lastUpdate
                )
            } else {
                _uiState.value = UiState.Error("No se pudo obtener la información del Asset")
            }
        }
    }
}
