package com.example.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navigateToDashBoard: (userName: String) -> Unit) {

    val userName = remember { mutableStateOf("") }

    Column {
        Text(
            text = "Sign In"
        )
        TextField(
            value = userName.value,
            onValueChange = { userName.value = it},
            Modifier.padding(8.dp),
            label = { Text(text = "Enter any name") }
        )

        Button(onClick = { navigateToDashBoard(userName.value.ifEmpty { "No Name" }) }) {
            Text(text = "Proceed")
        }

    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen {}
}