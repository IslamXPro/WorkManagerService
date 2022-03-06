package com.example.workmanagerservice.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagerservice.databinding.ItemRvBinding
import com.example.workmanagerservice.entity.User
import java.util.*

class MyAdapter(private val list:List<User>, val rvItemClick: RvItemClick)
    : RecyclerView.Adapter<MyAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvBinding): RecyclerView.ViewHolder(itemRv.root){

        @SuppressLint("SetTextI18n")
        fun onBind(user: User, position: Int){
            itemRv.itemId.text = user.id.toString()
            itemRv.itemId.setSingleLine()
            itemRv.itemId.ellipsize = TextUtils.TruncateAt.MARQUEE
            itemRv.itemId.marqueeRepeatLimit = -1
            itemRv.itemId.isSelected = true

            itemRv.itemDelete.setOnClickListener {
                rvItemClick.itemDelete(user, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}
interface RvItemClick{
    fun itemDelete(user: User, position: Int)
}