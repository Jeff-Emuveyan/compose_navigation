package com.example.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashBoardScreen(viewModel: DashBoardScreenViewModel = hiltViewModel()) {

    Text(
        text = "Hello, ${viewModel.userName}"
    )
}