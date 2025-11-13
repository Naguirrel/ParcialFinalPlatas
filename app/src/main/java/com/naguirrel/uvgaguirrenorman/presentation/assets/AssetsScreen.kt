package com.naguirrel.uvgaguirrenorman.presentation.assets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.naguirrel.uvgaguirrenorman.domain.model.Asset
import com.naguirrel.uvgaguirrenorman.presentation.state.UiState
import com.naguirrel.uvgaguirrenorman.presentation.util.indicadorTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(
    viewModel: AssetsViewModel,
    onAssetClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CryptoTracker") },
                actions = {
                    IconButton(onClick = { viewModel.loadAssets() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.saveOffline() }
            ) {
                Text("Ver offline")
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    val success = state as UiState.Success<List<Asset>>
                    Column {
                        indicadorTexto(success.fromLocal, success.lastUpdate)?.let { txt ->
                            Text(
                                text = txt,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                        LazyColumn {
                            items(success.data) { asset ->
                                AssetRow(asset = asset, onClick = { onAssetClick(asset.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AssetRow(asset: Asset, onClick: () -> Unit) {
    val change = asset.changePercent24Hr ?: 0.0
    val isPositive = change >= 0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = asset.name, style = MaterialTheme.typography.titleMedium)
                Text(text = asset.symbol, style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = asset.priceUsd?.let { "$" + "%.2f".format(it) } ?: "--",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "%.2f%%".format(change),
                    color = if (isPositive) Color(0xFF00C853) else Color(0xFFD50000),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
