package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.LoginScreen(state:AuthUiState) {
    with(state){
        Header("WELCOME BACK", "Login", ::back)
        Spacer(Modifier.height(36.dp))
        Brand()
        Spacer(Modifier.height(28.dp))
        Title("Good to see you again.")
        Body("Your next conversation is waiting.")
        Spacer(Modifier.height(30.dp))
        Field("Email", email, {
            email = it
        }, emailField = true)
        var password by rememberSaveable {
            mutableStateOf("")
        }
        Field("Password", password, {
            password = it
        }, secret = true)
        TextButton(onClick = {
            go("reset")
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Forgot password?", color = Gold)
        }
        Spacer(Modifier.height(10.dp))
        Action("Login") {
            go("workspace")
        }
        TextButton(onClick = {
            go("signup")
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("New to Jeel? Sign up", color = Gold)
        }
        Spacer(Modifier.height(48.dp))
        Body("Your experience and your data stay yours.")
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun LoginScreenPreview() {
    PreviewScreen { LoginScreen(AuthUiState()) }
}
