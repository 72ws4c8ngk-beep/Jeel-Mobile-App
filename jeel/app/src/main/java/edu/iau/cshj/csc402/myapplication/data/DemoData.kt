package edu.iau.cshj.csc402.myapplication.data

import edu.iau.cshj.csc402.myapplication.R

internal const val Bio = "I spent 28 years turning customer problems into meaningful products, most recently leading product teams in fintech. Now retired, I help emerging professionals find their direction, build product thinking, and lead with confidence."
internal const val Goal = "Land an associate product manager role after graduation."
internal const val Note = "I'm exploring product management after graduation. I'd love help identifying the skills and first steps for an associate PM role."
internal data class Mentor(val name:String,val job:String,val area:String,val years:Int,val rating:String,val image:Int,val tags:List<String>,val available:Boolean=true)
internal val mentors get() = listOf(
Mentor("Dr. Omar Haddad","Retired Product Director","Product strategy",28,"4.9",R.drawable.jeel_omar,listOf("Career roadmap","Leadership")),
Mentor("Leila Mansour","Head of Design · Noon","UX & design",16,"4.8",R.drawable.jeel_leila,listOf("Portfolio review","UX research")),
Mentor("Nadia Saleh","Senior Data Scientist","Data & analytics",18,"4.9",R.drawable.jeel_nadia,listOf("Data careers","Technical skills"),false)
)
