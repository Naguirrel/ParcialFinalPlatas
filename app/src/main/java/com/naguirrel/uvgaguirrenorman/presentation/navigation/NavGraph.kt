package com.naguirrel.uvgaguirrenorman.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.naguirrel.uvgaguirrenorman.domain.repository.AssetRepository
import com.naguirrel.uvgaguirrenorman.presentation.assets.AssetsScreen
import com.naguirrel.uvgaguirrenorman.presentation.assets.AssetsViewModel
import com.naguirrel.uvgaguirrenorman.presentation.detail.AssetDetailScreen
import com.naguirrel.uvgaguirrenorman.presentation.detail.AssetDetailViewModel

@Composable
fun AppNavHost(
    repo: AssetRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "assets") {
        composable("assets") {
            val vm: AssetsViewModel = viewModel(
                factory = AssetsViewModelFactory(repo)
            )
            AssetsScreen(
                viewModel = vm,
                onAssetClick = { id ->
                    navController.navigate("assetDetail/$id")
                }
            )
        }
        composable(
            route = "assetDetail/{assetId}",
            arguments = listOf(
                navArgument("assetId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val vm: AssetDetailViewModel = viewModel(
                factory = AssetDetailViewModelFactory(repo, backStackEntry)
            )
            AssetDetailScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
