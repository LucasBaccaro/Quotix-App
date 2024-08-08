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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import authentication.data.remote.dtos.MemberRequest
import authentication.data.remote.dtos.OwnerRequest
import authentication.ui.AuthViewModel

@Composable
fun CreateScreen(authViewModel: AuthViewModel, onBack: () -> Unit = {}) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("member") }
    var purposeName by remember { mutableStateOf("") }
    var feeAmount by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }

    val state = authViewModel.uiState.collectAsState()

    if (state.value.isLoading) {
        CircularProgressIndicator()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
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
            Button(onClick = { role = "owner" }) {
                Text("Owner")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { role = "member" }) {
                Text("Member")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (role == "owner") {
            TextField(
                value = purposeName,
                onValueChange = { purposeName = it },
                label = { Text("Purpose Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = feeAmount,
                onValueChange = { feeAmount = it },
                label = { Text("Fee Amount") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            TextField(
                value = inviteCode,
                onValueChange = { inviteCode = it },
                label = { Text("Invite Code") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (role == "owner") {
                    authViewModel.createAccountOwner(
                        OwnerRequest(
                            email = username,
                            password = password,
                            role = "owner",
                            purposeName = purposeName,
                            feeAmount = feeAmount.toDouble()
                        )
                    )
                } else {
                    authViewModel.createAccountMember(
                        MemberRequest(
                            email = username,
                            password = password,
                            role = "member",
                            inviteCode = inviteCode
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create account")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


