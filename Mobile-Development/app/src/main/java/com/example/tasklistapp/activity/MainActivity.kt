package com.example.tasklistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklistapp.adapter.TaskAdapter
import com.example.tasklistapp.data.response.UserItem
import com.example.tasklistapp.databinding.ActivityMainBinding
import com.example.tasklistapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvTask.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvTask.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { users ->
            setUserData(users)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(users: List<UserItem>) {
        val adapter = TaskAdapter()
        adapter.submitList(users)
        binding.rvTask.adapter = adapter
    }
}