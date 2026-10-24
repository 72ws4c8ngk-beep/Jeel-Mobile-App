package edu.iau.cshj.csc402.myapplication.ui.screens.auth

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

@Composable
internal fun ColumnScope.SignUpScreen(state:AuthUiState) {
    with(state){
        Header("A NEW CHAPTER", "Create your account", ::back)
        Title("Start with a connection.")
        Body("A few details to make your mentorship journey personal.")
        Spacer(Modifier.height(20.dp))
        Field("Full name", fullName, {
            fullName = it
        })
        Field("Email", email, {
            email = it
        }, emailField = true)
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var confirmation by rememberSaveable {
            mutableStateOf("")
        }
        Field("Password", password, {
            password = it
        }, secret = true)
        Body("Use at least 8 characters, a number and a symbol.")
        Spacer(Modifier.height(16.dp))
        Field("Confirm password", confirmation, {
            confirmation = it
        }, secret = true)
        var agreed by rememberSaveable {
            mutableStateOf(false)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(agreed, {
                agreed = it
            })
            Text("I agree to the Terms of service and Privacy policy.", fontSize = 12.sp, color = Slate)
        }
        Spacer(Modifier.height(12.dp))
        Action("Create account", enabled = true) {
            go("role")
        }
        TextButton(onClick = {
            go("login")
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Already have an account? Login", color = Gold)
        }
    }
}

@Preview(showBackground=true,widthDp=393,heightDp=852)
@Composable
private fun SignUpScreenPreview() {
    PreviewScreen { SignUpScreen(AuthUiState()) }
}
