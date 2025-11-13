package com.naguirrel.uvgaguirrenorman.presentation.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naguirrel.uvgaguirrenorman.domain.model.Asset
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetRepository
import com.naguirrel.uvgaguirrenorman.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssetsViewModel(
    private val repo: AssetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Asset>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Asset>>> = _uiState

    init {
        loadAssets()
    }

    fun loadAssets() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repo.getAssets()
            val data = result.data
            if (data != null && data.isNotEmpty()) {
                _uiState.value = UiState.Success(
                    data = data,
                    fromLocal = result.fromLocal,
                    lastUpdate = result.lastUpdate
                )
            } else {
                _uiState.value = UiState.Error("No se pudo obtener información de los Assets")
            }
        }
    }

    fun saveOffline() {
        viewModelScope.launch {
            val res = repo.saveOfflineData()
            if (res.isSuccess) {
                loadAssets()
            }
        }
    }
}
