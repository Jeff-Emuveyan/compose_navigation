package com.example.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(navigateToSignUp: () -> Unit,
               navigateToSignIn: () -> Unit) {

    Column(modifier = Modifier.padding(20.dp)) {
        Button(onClick = navigateToSignUp) {
            Text(text = "SignUp")
        }

        Button(onClick = navigateToSignIn) {
            Text(text = "SignIn")
        }
    }
}