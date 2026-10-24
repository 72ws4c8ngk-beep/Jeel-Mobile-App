package edu.iau.cshj.csc402.myapplication.state

import androidx.compose.runtime.*
import edu.iau.cshj.csc402.myapplication.ui.components.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import androidx.compose.runtime.saveable.mapSaver

@Stable
internal class AuthUiState {
    var screen by mutableStateOf("splash")
    var role by mutableStateOf("Mentee")
    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var notice by mutableStateOf("")
    var onboardingProfile by mutableStateOf(hashMapOf<String,String>())
    fun go(next:String){
        notice=""
        screen=next
    }
    fun back(){
        go(when(screen){
            "login","signup"->"welcome"
            "role"->"signup"
            "profile"->"role"
            "reset","sent"->"login"
            else->"welcome"
        })
    }
    companion object {
        @Suppress("UNCHECKED_CAST")
        val Saver=mapSaver<AuthUiState>(save={
            state->mapOf("screen" to state.screen,
            "role" to state.role,
            "fullName" to state.fullName,
            "email" to state.email,
            "notice" to state.notice,
            "onboardingProfile" to state.onboardingProfile)
        },restore={
            values->AuthUiState().apply{
                screen=values["screen"] as String
                role=values["role"] as String
                fullName=values["fullName"] as String
                email=values["email"] as String
                notice=values["notice"] as String
                onboardingProfile=values["onboardingProfile"] as HashMap<String,String>
            }
        })
    }
}
