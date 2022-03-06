package com.example.workmanagerservice.dao

import androidx.room.*
import com.example.workmanagerservice.entity.User
import io.reactivex.Flowable

@Dao
interface UserDao {


    @Insert
    fun addUser(user: User)

    @Query("select * from user")
    fun getAll(): Flowable<List<User>>

    @Delete
    fun deleteNew(news: User)

    @Query("select * from user where id=:id")
    fun getNewsById(id:Int):User
}