package com.naguirrel.uvgaguirrenorman.data.remote

import com.naguirrel.uvgaguirrenorman.data.remote.dto.AssetDto
import com.naguirrel.uvgaguirrenorman.data.remote.dto.AssetListResponseDto
import com.naguirrel.uvgaguirrenorman.data.remote.dto.AssetSingleResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CoinCapApi {
    suspend fun getAssets(): List<AssetDto>
    suspend fun getAssetById(id: String): AssetDto
}

class CoinCapApiImpl(
    private val client: HttpClient = KtorClientProvider.client
) : CoinCapApi {

    override suspend fun getAssets(): List<AssetDto> {
        val response: AssetListResponseDto = client.get("assets").body()
        return response.data
    }

    override suspend fun getAssetById(id: String): AssetDto {
        val response: AssetSingleResponseDto = client.get("assets/$id").body()
        return response.data
    }
}
