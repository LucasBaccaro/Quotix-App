package authentication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import authentication.data.remote.dtos.LoginRequest
import authentication.ui.AuthUiState
import authentication.ui.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    goToCreateAccount: () -> Unit,
    goToHome: (String) -> Unit
) {
    val state by authViewModel.uiState.collectAsStateWithLifecycle()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.user) {
        state.user?.let {
            goToHome(it.id.toString())
            authViewModel.cleanState()
        }
    }

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }
        else -> {
            LoginContent(
                username = username,
                password = password,
                onUsernameChange = { username = it },
                onPasswordChange = { password = it },
                onLogin = { authViewModel.login(LoginRequest(username, password)) },
                goToCreateAccount = goToCreateAccount,
                state = state
            )
        }
    }
}

@Composable
fun LoginContent(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    goToCreateAccount: () -> Unit,
    state: AuthUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = goToCreateAccount, modifier = Modifier.fillMaxWidth()) {
            Text("Create Account")
        }
        Spacer(modifier = Modifier.height(8.dp))
        state.error?.let { Text(it.toString(), color = Color.Red) }
        state.message?.let { Text(it.toString(), color = Color.Green) }
    }
}