package com.example.tasklistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklistapp.adapter.TaskAdapter
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.databinding.ActivityListTaskBinding
import com.example.tasklistapp.viewmodel.ListTaskViewModel

class ListTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.hide()

        val listTaskViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ListTaskViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvTask.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvTask.addItemDecoration(itemDecoration)

        listTaskViewModel.listTask.observe(this) { tasks ->
            setTaskData(tasks)
        }
        listTaskViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setTaskData(tasks: List<RequestResponseItem>) {
        val adapter = TaskAdapter()
        adapter.submitList(tasks)
        binding.rvTask.adapter = adapter
    }

}