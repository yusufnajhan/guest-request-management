package com.example.tasklistapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistapp.activity.DetailActivity
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.databinding.ItemTaskBinding

class TaskAdapter : ListAdapter<RequestResponseItem, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, holder)
//        val message = task.message
        val id = task.id

        holder.itemView.setOnClickListener{
            val detailIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.ID, id)
            holder.itemView.context.startActivity(detailIntent)
        }
    }
    class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: RequestResponseItem, holder: MyViewHolder){
            val maxLength = 40
            val message = task.message
            val displayMessage = if(message.length > maxLength) {
                "${message.substring(0, maxLength)}..."
            } else {
                message
            }

            binding.tvItemMessage.text = displayMessage
            binding.tvItemId.text = buildString {
                append("Task #")
                append(task.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RequestResponseItem>() {
            override fun areItemsTheSame(oldItem: RequestResponseItem, newItem: RequestResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: RequestResponseItem, newItem: RequestResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}