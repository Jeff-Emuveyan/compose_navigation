package com.example.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {

    //We get the userName that was parsed in the 'route' to DashBoardScreen composable:
    // https://developer.android.com/jetpack/compose/navigation#retrieving-complex-data
    val userName: String = savedStateHandle["userName"] ?: "Error"
}