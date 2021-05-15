package com.example.splitebill.model

data class Event(val refNumber:String="" ,val eventName: String = "", val eventDescription: String = "", val eventAmount: Int = 0,
                 val people: HashMap<String,String> = HashMap<String,String>())