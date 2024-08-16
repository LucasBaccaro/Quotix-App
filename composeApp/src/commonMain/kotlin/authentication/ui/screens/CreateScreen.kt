package authentication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.OwnerRequest
import authentication.ui.AuthUiState
import authentication.ui.AuthViewModel

@Composable
fun CreateScreen(
    authViewModel: AuthViewModel,
    onBack: () -> Unit = {},
    goToHome: (String) -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("member") }
    var purposeName by remember { mutableStateOf("") }
    var feeAmount by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }

    val state = authViewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(state.userId) {
        state.userId?.let {
            goToHome(it)
            authViewModel.cleanState()
        }
    }

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }
        else -> {
            CreateContent(
                username = username,
                password = password,
                role = role,
                purposeName = purposeName,
                feeAmount = feeAmount,
                inviteCode = inviteCode,
                onUsernameChange = { username = it },
                onPasswordChange = { password = it },
                onRoleChange = { role = it },
                onPurposeNameChange = { purposeName = it },
                onFeeAmountChange = { feeAmount = it },
                onInviteCodeChange = { inviteCode = it },
                onCreateAccount = {
                    if (role == "owner") {
                        authViewModel.createAccountOwner(
                            OwnerRequest(username, password, role, purposeName, feeAmount.toDouble())
                        )
                    } else {
                        authViewModel.createAccountMember(
                            MemberRequest(username, password, role, inviteCode)
                        )
                    }
                },
                state = state
            )
        }
    }
}

@Composable
fun CreateContent(
    username: String,
    password: String,
    role: String,
    purposeName: String,
    feeAmount: String,
    inviteCode: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
    onPurposeNameChange: (String) -> Unit,
    onFeeAmountChange: (String) -> Unit,
    onInviteCodeChange: (String) -> Unit,
    onCreateAccount: () -> Unit,
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
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Role: ")
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onRoleChange("owner") }) { Text("Owner") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onRoleChange("member") }) { Text("Member") }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (role == "owner") {
            TextField(
                value = purposeName,
                onValueChange = onPurposeNameChange,
                label = { Text("Purpose Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = feeAmount,
                onValueChange = onFeeAmountChange,
                label = { Text("Fee Amount") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            TextField(
                value = inviteCode,
                onValueChange = onInviteCodeChange,
                label = { Text("Invite Code") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onCreateAccount, modifier = Modifier.fillMaxWidth()) {
            Text("Create account")
        }
        Spacer(modifier = Modifier.height(8.dp))
        state.error?.let { Text(it.toString(), color = Color.Red) }
        state.message?.let { Text(it.toString(), color = Color.Green) }
    }
}


