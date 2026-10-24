package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.iau.cshj.csc402.myapplication.data.*
import edu.iau.cshj.csc402.myapplication.ui.theme.*
import java.util.Calendar

@Composable internal fun PageHead(kicker:String,title:String,back:(()->Unit)?=null,trailing:(@Composable ()->Unit)?=null){
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){
        if(back!=null){
            TextButton(onClick=back,modifier=Modifier.width(32.dp),contentPadding=PaddingValues(0.dp)){
                Text("←",fontSize=23.sp)
            }
            Spacer(Modifier.width(8.dp))
        }
        Column(Modifier.weight(1f)){
            Text(kicker,color=Gold,fontSize=10.sp)
            Text(title,color=Navy,fontSize=21.sp,lineHeight=26.sp)
        }
        trailing?.invoke()
    }
}
@Composable internal fun Heading(value:String,size:Int=20){
    Text(value,color=Navy,fontSize=size.sp,lineHeight=(size+6).sp)
}
@Composable internal fun BodyText(value:String){
    Text(value,color=Slate,fontSize=14.sp,lineHeight=21.sp)
}
@Composable internal fun Caption(value:String){
    Text(value,color=Slate,fontSize=11.sp,lineHeight=16.sp)
}
@Composable internal fun Section(title:String,link:String,click:()->Unit){
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){
        Text(title,color=Navy,fontSize=16.sp,modifier=Modifier.weight(1f))
        TextButton(onClick=click,contentPadding=PaddingValues(start=8.dp)){
            Text(link,color=Gold,fontSize=11.sp)
        }
    }
}
@Composable internal fun Panel(color:Color=Color.White,content:@Composable ColumnScope.()->Unit){
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(color).then(if(color==Color.White)Modifier.border(1.dp,Edge,RoundedCornerShape(18.dp))else Modifier).padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp),content=content)
}
@Composable internal fun Info(title:String,body:String){
    Panel(Mist){
        Text(title,color=Navy,fontWeight=FontWeight.SemiBold,fontSize=13.sp)
        Text(body,color=Slate,fontSize=12.sp,lineHeight=18.sp)
    }
}
@Composable internal fun Avatar(image:Int,size:Int=42){
    Image(painterResource(image),contentDescription=null,modifier=Modifier.size(size.dp).clip(CircleShape),contentScale=ContentScale.Crop)
}
@Composable internal fun Person(name:String,job:String,image:Int){
    Row(verticalAlignment=Alignment.CenterVertically){
        Avatar(image)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)){
            Text(name,color=Navy,fontSize=14.sp)
            Text(job,color=Slate,fontSize=11.sp,lineHeight=16.sp)
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable internal fun Labels(values:List<String>){
    FlowRow(horizontalArrangement=Arrangement.spacedBy(6.dp),verticalArrangement=Arrangement.spacedBy(6.dp)){
        values.forEach{
            value->Text(value,color=Slate,fontSize=11.sp,modifier=Modifier.border(1.dp,Edge,RoundedCornerShape(50)).padding(horizontal=11.dp,vertical=9.dp))
        }
    }
}
@Composable internal fun ProfileHero(name:String,subtitle:String,image:Int,badge:String){
    Column(Modifier.fillMaxWidth(),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(10.dp)){
        Avatar(image,76)
        Heading(name,23)
        Caption(subtitle)
        Badge(badge)
    }
}
@Composable internal fun Badge(value:String){
    val c=when(value){
        "Pending"->Warm
        "Completed","Verified mentor"->Color(0xFFE5F1ED)
        "Cancelled","Declined"->Rose
        else->Mist
    }
    Text("• $value",color=when(c){
        Warm->Gold
        Rose->Color(0xFFB36A60)
        else->if(c==Mist)Navy else Green
    },fontSize=10.sp,fontWeight=FontWeight.SemiBold,modifier=Modifier.clip(RoundedCornerShape(5.dp)).background(c).padding(horizontal=8.dp,vertical=4.dp))
}
@Composable internal fun Stats(items:List<Pair<String,String>>){
    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){
        items.forEach{
            (number,label)->Column(Modifier.weight(1f).clip(RoundedCornerShape(14.dp)).background(Color.White).border(1.dp,Edge,RoundedCornerShape(14.dp)).padding(horizontal=8.dp,vertical=16.dp),horizontalAlignment=Alignment.CenterHorizontally){
                Text(number,fontSize=23.sp,color=Navy)
                Spacer(Modifier.height(8.dp))
                Text(label,color=Slate,fontSize=10.sp,textAlign=TextAlign.Center)
            }
        }
    }
}
@Composable internal fun MentorCard(m:Mentor,click:()->Unit){
    Box(Modifier.clickable(onClick=click)){
        Panel{
            Person(m.name,m.job,m.image)
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
                Caption("${m.area} · ${m.years} years")
                Caption("☆ ${m.rating}")
            }
            Labels(m.tags)
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
                Badge(if(m.available)"Available" else "Unavailable")
                Text("↗",color=Slate)
            }
        }
    }
}
@Composable internal fun SessionCard(name:String,job:String,topic:String,status:String,image:Int,date:String,click:()->Unit){
    Box(Modifier.clickable(onClick=click)){
        Panel{
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){
                Text("1:1 MENTORSHIP · 45 min",fontSize=10.sp,color=Navy,fontWeight=FontWeight.SemiBold)
                Badge(status)
            }
            Person(name,job,image)
            Heading(topic,16)
            Caption("▦ $date")
        }
    }
}
@Composable internal fun Detail(icon:String,label:String,value:String){
    Row{
        Text(icon,color=Slate,modifier=Modifier.width(25.dp))
        Column{
            Caption(label)
            Text(value,color=Navy,fontSize=13.sp,lineHeight=20.sp)
        }
    }
}
@Composable internal fun SessionDetails(date:String,time:String,topic:String){
    Detail("▦","Date",date)
    Detail("◷","Time","$time · 45 min · Arabia Standard Time (UTC+3)")
    Detail("♧","Topic",topic)
}
@Composable internal fun ReviewCard(){
    Panel{
        Text("★★★★★",color=Gold,fontSize=16.sp)
        BodyText("“Omar helped me turn a vague ambition into a realistic plan. I left knowing exactly what to work on next.”")
        Caption("Sara Khalid · Graduate · 8 Oct 2026")
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable internal fun Chips(values:List<String>,selected:String,choose:(String)->Unit){
    FlowRow(horizontalArrangement=Arrangement.spacedBy(6.dp),verticalArrangement=Arrangement.spacedBy(6.dp)){
        values.forEach{
            v->Surface(onClick={
                choose(v)
            },shape=RoundedCornerShape(50),color=if(v==selected)Navy else Color.White,border=BorderStroke(1.dp,if(v==selected)Navy else Edge)){
                Text(v,Modifier.padding(horizontal=11.dp,vertical=9.dp),color=if(v==selected)Color.White else Slate,fontSize=11.sp)
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable internal fun MultiChips(values:List<String>,selected:List<String>,choose:(List<String>)->Unit){
    FlowRow(horizontalArrangement=Arrangement.spacedBy(6.dp),verticalArrangement=Arrangement.spacedBy(6.dp)){
        (values+selected).distinct().forEach{
            v->Surface(onClick={
                choose(if(v in selected)selected-v else selected+v)
            },shape=RoundedCornerShape(50),color=if(v in selected)Navy else Color.White,border=BorderStroke(1.dp,Edge)){
                Text(v,Modifier.padding(horizontal=11.dp,vertical=9.dp),color=if(v in selected)Color.White else Slate,fontSize=11.sp)
            }
        }
    }
}
@Composable internal fun Segments(values:List<String>,selected:String,choose:(String)->Unit){
    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Mist).padding(4.dp)){
        values.forEach{
            v->Box(Modifier.weight(1f).clip(RoundedCornerShape(9.dp)).background(if(v==selected)Color.White else Color.Transparent).clickable{
                choose(v)
            }
            .padding(vertical=12.dp),contentAlignment=Alignment.Center){
                Text(v,fontSize=11.sp,color=Navy)
            }
        }
    }
}
@Composable internal fun SearchBox(value:String,change:(String)->Unit){
    OutlinedTextField(value,change,modifier=Modifier.fillMaxWidth(),singleLine=true,placeholder={
        Text("Search by name or expertise",fontSize=12.sp)
    },leadingIcon={
        Text("⌕",fontSize=23.sp,color=Slate)
    },shape=RoundedCornerShape(12.dp),colors=OutlinedTextFieldDefaults.colors(unfocusedContainerColor=Color.White,focusedContainerColor=Color.White,unfocusedBorderColor=Edge))
}
@Composable internal fun EditBox(label:String,value:String,lines:Int=1,change:(String)->Unit){
    Column(verticalArrangement=Arrangement.spacedBy(6.dp)){
        Caption(label)
        OutlinedTextField(value,change,modifier=Modifier.fillMaxWidth(),singleLine=lines==1,minLines=lines,shape=RoundedCornerShape(10.dp),textStyle=LocalTextStyle.current.copy(fontSize=14.sp,color=Navy),colors=OutlinedTextFieldDefaults.colors(unfocusedContainerColor=Color.White,focusedContainerColor=Color.White,unfocusedBorderColor=Edge))
    }
}
@Composable internal fun Picker(label:String,value:String,options:List<String>,choose:(String)->Unit){
    var expanded by remember{
        mutableStateOf(false)
    }
    Column(verticalArrangement=Arrangement.spacedBy(6.dp)){
        Caption(label)
        Box{
            Surface(onClick={
                expanded=true
            },shape=RoundedCornerShape(10.dp),border=BorderStroke(1.dp,Edge),color=Color.White){
                Row(Modifier.fillMaxWidth().padding(16.dp),horizontalArrangement=Arrangement.SpaceBetween){
                    Text(value,color=Navy,fontSize=13.sp)
                    Text("⌄",color=Slate)
                }
            }
            DropdownMenu(expanded,{
                expanded=false
            }){
                options.forEach{
                    option->DropdownMenuItem(text={
                        Text(option)
                    },onClick={
                        choose(option)
                        expanded=false
                    })
                }
            }
        }
    }
}
internal fun monthName(month:Int)=listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")[month-1]
@Composable internal fun CalendarCard(year:Int,month:Int,date:Int,select:(Int)->Unit,changeMonth:(Int,Int)->Unit){
    val cal=Calendar.getInstance().apply{
        set(year,month-1,1)
    }
    val length=cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val offset=(cal.get(Calendar.DAY_OF_WEEK)+5)%7
    Panel{
        Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween){
            TextButton(onClick={
                changeMonth(if(month==1)year-1 else year,if(month==1)12 else month-1)
            }){
                Text("‹")
            }
            Text("${monthName(month)} $year",color=Navy,fontSize=14.sp)
            TextButton(onClick={
                changeMonth(if(month==12)year+1 else year,if(month==12)1 else month+1)
            }){
                Text("›")
            }
        }
        Row{
            listOf("M","T","W","T","F","S","S").forEach{
                Text(it,Modifier.weight(1f),textAlign=TextAlign.Center,color=Slate,fontSize=11.sp)
            }
        }
        repeat((offset+length+6)/7){
            week->Row{
                repeat(7){
                    day->val n=week*7+day-offset+1
                    Box(Modifier.weight(1f).height(38.dp).padding(2.dp).clip(RoundedCornerShape(8.dp)).background(if(n==date)Navy else Color.Transparent).then(if(n in 1..length)Modifier.clickable{
                        select(n)
                    }
                    else Modifier),contentAlignment=Alignment.Center){
                        if(n in 1..length)Text(n.toString(),color=if(n==date)Color.White else Slate,fontSize=12.sp)
                    }
                }
            }
        }
        Caption("Times shown in Arabia Standard Time (UTC+3)")
    }
}
@Composable internal fun ProgressCard(title:String,value:Float,subtitle:String){
    Panel{
        Row{
            Text(title,color=Navy,fontSize=13.sp,modifier=Modifier.weight(1f))
            Caption("${(value*100).toInt()}%")
        }
        LinearProgressIndicator(progress={
            value
        },modifier=Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),color=Navy,trackColor=Mist)
        Caption(subtitle)
    }
}
@Composable internal fun ToggleRow(title:String,checked:Boolean,change:(Boolean)->Unit){
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){
        Text(title,color=Navy,fontSize=13.sp,modifier=Modifier.weight(1f))
        Switch(checked,change)
    }
}
@Composable internal fun SettingsRow(title:String,subtitle:String,click:()->Unit){
    Row(Modifier.fillMaxWidth().clickable(onClick=click).padding(vertical=10.dp),verticalAlignment=Alignment.CenterVertically){
        Column(Modifier.weight(1f)){
            BodyText(title)
            Caption(subtitle)
        }
        Text("›",fontSize=25.sp,color=Slate)
    }
}
@Composable internal fun TagEditor(title:String,button:String,options:List<String>,selected:List<String>,change:(List<String>)->Unit){
    var open by rememberSaveable{
        mutableStateOf(false)
    }
    var draft by rememberSaveable{
        mutableStateOf("")
    }
    Heading(title,18)
    MultiChips(options,selected,change)
    Action(button,secondary=true){
        open=true
    }
    if(open)AlertDialog(onDismissRequest={
        open=false
    },title={
        Text(button)
    },text={
        EditBox("Name",draft){
            draft=it
        }
    },confirmButton={
        TextButton(onClick={
            if(draft.isNotBlank()){
                change((selected+draft.trim()).distinct())
                draft=""
                open=false
            }
        }){
            Text("Add")
        }
    },dismissButton={
        TextButton(onClick={
            open=false
        }){
            Text("Cancel")
        }
    })
}
@Composable internal fun DangerButton(title:String,click:()->Unit){
    Button(onClick=click,modifier=Modifier.fillMaxWidth().heightIn(min=50.dp),shape=RoundedCornerShape(12.dp),colors=ButtonDefaults.buttonColors(containerColor=Rose,contentColor=Color(0xFFB36A60))){
        Text(title)
    }
}
@Composable internal fun JeelErrorBanner(title:String,body:String,retry:()->Unit){
    Surface(color=Rose,shape=RoundedCornerShape(12.dp)){
        Row(Modifier.fillMaxWidth().padding(16.dp),verticalAlignment=Alignment.CenterVertically){
            Text("ⓘ",color=Color(0xFFBE6A5F),fontSize=23.sp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)){
                Text(title,color=Navy,fontWeight=FontWeight.SemiBold,fontSize=13.sp)
                Caption(body)
            }
            TextButton(onClick=retry){
                Text("Retry",color=Color(0xFFBE6A5F))
            }
        }
    }
}
