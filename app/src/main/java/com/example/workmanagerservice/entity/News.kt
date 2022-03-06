package com.example.workmanagerservice.entity

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
 class User{
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null

   @SuppressLint("SimpleDateFormat")
   var time = SimpleDateFormat("(MM-dd) (HH:mm:ssSSS)").format(Date())
}