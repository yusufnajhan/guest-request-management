package com.example.tasklistapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistapp.data.response.UserItem
import com.example.tasklistapp.databinding.ItemTaskBinding

class TaskAdapter : ListAdapter<UserItem, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, holder)
        val username = user.login

//        holder.itemView.setOnClickListener{
//            val detailIntent = Intent(holder.itemView.context, DetailActivity::class.java)
//            detailIntent.putExtra(DetailActivity.USERNAME, username)
//            holder.itemView.context.startActivity(detailIntent)
//        }
    }
    class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem, holder: MyViewHolder){
            binding.tvItemUsername.text = "${user.login}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}