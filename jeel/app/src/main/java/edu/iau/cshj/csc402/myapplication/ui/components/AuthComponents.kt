package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.ui.theme.*

@Composable internal fun Brand() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(Modifier.size(42.dp), color = Navy, shape = RoundedCornerShape(15.dp)) {
            Box(contentAlignment = Alignment.Center) {
                JeelLogoIcon(Modifier.size(28.dp),Color.White)
            }
        }
        Spacer(Modifier.width(12.dp))
        Text("Jeel", fontSize = 25.sp, color = Navy)
        Text(" | ", color = Gold, fontSize = 26.sp)
        Text("جيل", color = Navy, fontSize = 25.sp)
    }
}
@Composable internal fun Header(kicker: String, title: String, back: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(bottom = 28.dp), verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = back, contentPadding = PaddingValues(0.dp), modifier = Modifier.width(32.dp)) {
            Text("←", fontSize = 24.sp, color = Navy)
        }
        Spacer(Modifier.width(8.dp))
        Column {
            Text(kicker, color = Gold, fontSize = 11.sp)
            Text(title, color = Navy, fontSize = 21.sp)
        }
    }
}
@Composable internal fun Title(value: String) {
    Text(value, color = Navy, fontSize = 30.sp, lineHeight = 42.sp, modifier = Modifier.padding(bottom = 14.dp))
}
@Composable internal fun Body(value: String) {
    Text(value, color = Slate, fontSize = 15.sp, lineHeight = 22.sp)
}
@Composable internal fun Footer(value: String) {
    Text(value, Modifier.fillMaxWidth().padding(top = 24.dp), color = Slate, fontSize = 12.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
}
@Composable internal fun Action(label: String, secondary: Boolean = false, enabled: Boolean = true, onClick: () -> Unit) {
    val scene=LocalUiScene.current
    val busy=!secondary&&(scene.state in listOf("Saving","Sending","Processing")||scene.state=="Loading"&&scene.screen in listOf(3,4,6))
    val noSlot=scene.state=="No slot selected"&&label.startsWith("Confirm")
    Button(onClick, enabled = enabled&&!busy&&!noSlot, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp), shape = RoundedCornerShape(12.dp), border = if(secondary) androidx.compose.foundation.BorderStroke(1.dp, Edge) else null, colors = ButtonDefaults.buttonColors(containerColor = if(secondary) Color.White else Navy, contentColor = if(secondary) Navy else Color.White)) {
        if(busy) {
            CircularProgressIndicator(Modifier.size(18.dp),strokeWidth=2.dp,color=Navy)
            Spacer(Modifier.width(10.dp))
        }
        Text(if(busy) when(scene.state) { "Saving"->"Saving…"; "Sending"->"Sending…"; "Processing"->"Processing…"; else->"Loading…" } else label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}
@Composable internal fun Field(label: String, value: String, change: (String) -> Unit, secret: Boolean = false, emailField: Boolean = false) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    val error=inputError(label)
    Text(label, color = Slate, fontSize = 13.sp, modifier = Modifier.padding(bottom = 8.dp))
    OutlinedTextField(inputDisplayValue(label,value), change, isError=error!=null, supportingText=if(error!=null) { { Text(error) } } else null, modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), singleLine = true, shape = RoundedCornerShape(12.dp), visualTransformation = if(secret && !visible && LocalUiScene.current.state!="Password visible") PasswordVisualTransformation() else VisualTransformation.None, keyboardOptions = KeyboardOptions(keyboardType = if(secret) KeyboardType.Password else if(emailField) KeyboardType.Email else KeyboardType.Text), trailingIcon = if(secret) {
        {
            TextButton(onClick = {
                visible = !visible
            }) {
                Text(if(visible) "Hide" else "Show", color = Slate, fontSize = 11.sp)
            }
        }
    }
    else null, colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Navy, unfocusedBorderColor = Edge, focusedTextColor = Navy, unfocusedTextColor = Navy))
}
@Composable internal fun RoleCard(role: String, description: String, selected: Boolean, choose: () -> Unit) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(if(selected) Color(0xFFE9EFF2) else Color.White).border(if(selected) 2.dp else 1.dp, if(selected) Navy else Edge, RoundedCornerShape(20.dp)).clickable(onClick = choose).padding(24.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(if(role == "Mentee") "◇" else "⋈", color = Navy, fontSize = 28.sp)
            RadioButton(selected, choose)
        }
        Spacer(Modifier.height(16.dp))
        Text("I'm a $role", color = Navy, fontSize = 23.sp)
        Spacer(Modifier.height(18.dp))
        Body(description)
    }
}
