package com.example.workmanagerservice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.example.workmanagerservice.adapter.MyAdapter
import com.example.workmanagerservice.adapter.RvItemClick
import com.example.workmanagerservice.database.AppDatabase
import com.example.workmanagerservice.databinding.ActivityMainBinding
import com.example.workmanagerservice.entity.User
import com.example.workmanagerservice.service.MyWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var appDatabase: AppDatabase
    lateinit var myAdapter: MyAdapter
    lateinit var list:ArrayList<User>

    @SuppressLint("RestrictedApi", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        list = ArrayList()
        myAdapter = MyAdapter(list, object : RvItemClick{
            override fun itemDelete(user: User, position: Int) {
                appDatabase.newDao().deleteNew(user)
                list.remove(user)
                myAdapter.notifyItemRemoved(position)
                myAdapter.notifyItemRangeChanged(position,list.size)
            }
        })
        binding.recycle.adapter = myAdapter

        binding.apply {

            btnOneTime.setOnClickListener {
                val user = User()
                val workerRequest = OneTimeWorkRequestBuilder<MyWork>()
                    .build()
                WorkManager.getInstance(this@MainActivity).enqueue(workerRequest)

                appDatabase = AppDatabase.getInstance(this@MainActivity)
                myAdapter = MyAdapter(list, object : RvItemClick {
                    override fun itemDelete(user: User, position: Int) {
                        appDatabase.newDao().deleteNew(user)
                        appDatabase.newDao().getNewsById(position)
                    }
                })

                appDatabase.newDao().getAll()
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Consumer<List<User>>,
                        io.reactivex.functions.Consumer<List<User>> {
                        override fun accept(t: List<User>?) {
                            myAdapter = MyAdapter(list, object : RvItemClick {
                                override fun itemDelete(user: User, position: Int) {
                                    appDatabase.newDao().deleteNew(user)
                                    appDatabase.newDao().getNewsById(position)
                                }
                            })
                        }
                    })
                binding.recycle.adapter = myAdapter
            }


            /*val workRepeat = PeriodicWorkRequestBuilder<MyWork>(1,TimeUnit.MINUTES)
            btnRepeat.setOnClickListener {
                WorkManager.getInstance(this@MainActivity).enqueue(workRepeat)
                Toast.makeText(this@MainActivity, "Repeat start", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun myNotification(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest = OneTimeWorkRequestBuilder<MyWork>().setConstraints(constraints).build()

        WorkManager.getInstance(this)
            .enqueue(myRequest)
    }
}