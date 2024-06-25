//AuthScreen.kt

package com.example.myfirebaseapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirebaseapplication.ui.theme.MyFirebaseApplicationTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen(viewModel: AuthViewModel = viewModel()) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> Text("Logged in as ${(authState as AuthState.Success).user?.email}")
            is AuthState.Error -> Text("Error: ${(authState as AuthState.Error).message}", color = MaterialTheme.colorScheme.error)
            else -> Unit
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.signIn(email, password) }) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.signUp(email, password) }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.signOut() }) {
            Text("Sign Out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    MyFirebaseApplicationTheme {
        AuthScreen()
    }
}
