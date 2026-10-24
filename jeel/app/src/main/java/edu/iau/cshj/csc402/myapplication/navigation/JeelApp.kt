package edu.iau.cshj.csc402.myapplication.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import edu.iau.cshj.csc402.myapplication.state.*

import edu.iau.cshj.csc402.myapplication.ui.screens.mentee.*
import edu.iau.cshj.csc402.myapplication.ui.screens.mentor.*
import edu.iau.cshj.csc402.myapplication.ui.screens.shared.*
import edu.iau.cshj.csc402.myapplication.ui.screens.auth.*
import kotlinx.coroutines.delay

@Composable
fun JeelApp() {
    val state=rememberSaveable(saver=AuthUiState.Saver){
        AuthUiState()
    }
    with(state){
        LaunchedEffect(screen){
            if(screen=="splash"){
                delay(1800)
                screen="welcome"
            }
        }
        BackHandler(screen!="welcome" && screen!="splash"){
            back()
        }
        JeelTheme {
            when(screen){
                "splash" -> SplashScreen()
                "profile" -> JeelOnboarding(role,fullName,{
                    go("role")
                },{
                    go("workspace")
                })
                "workspace" -> JeelWorkspace(role,"",""){
                    go("welcome")
                }
                else -> Surface(color=Cream,modifier=Modifier.fillMaxSize()) {
                    key(screen){
                        Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(20.dp)) {
                            when(screen){
                                "welcome" -> WelcomeScreen(state)
                                "login" -> LoginScreen(state)
                                "signup" -> SignUpScreen(state)
                                "role" -> RoleSelectionScreen(state)
                                "reset" -> ForgotPasswordScreen(state)
                                "sent" -> CheckEmailScreen(state)
                            }
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
