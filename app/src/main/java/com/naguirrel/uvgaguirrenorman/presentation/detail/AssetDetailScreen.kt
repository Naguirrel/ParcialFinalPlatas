package com.naguirrel.uvgaguirrenorman.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import com.naguirrel.uvgaguirrenorman.domain.model.Asset
import com.naguirrel.uvgaguirrenorman.presentation.state.UiState
import com.naguirrel.uvgaguirrenorman.presentation.util.indicadorTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailScreen(
    viewModel: AssetDetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (state) {
                is UiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is UiState.Error -> Text(
                    text = (state as UiState.Error).message,
                    modifier = Modifier.align(Alignment.Center)
                )
                is UiState.Success -> {
                    val success = state as UiState.Success<Asset>
                    val asset = success.data
                    Column(Modifier.padding(16.dp)) {
                        indicadorTexto(success.fromLocal, success.lastUpdate)?.let { txt ->
                            Text(
                                text = txt,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Text(asset.name, style = MaterialTheme.typography.headlineSmall)
                        Text(asset.symbol, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(16.dp))
                        InfoRow("Precio USD", asset.priceUsd?.let { "$" + "%.2f".format(it) } ?: "--")
                        InfoRow("Cambio 24h", asset.changePercent24Hr?.let { "%.2f%%".format(it) } ?: "--")
                        InfoRow("Supply", asset.supply?.toString() ?: "--")
                        InfoRow("Max Supply", asset.maxSupply?.toString() ?: "--")
                        InfoRow("Market Cap USD", asset.marketCapUsd?.let { "$" + "%.2f".format(it) } ?: "--")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
