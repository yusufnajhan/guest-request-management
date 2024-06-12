package com.example.tasklistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tasklistapp.R
import com.example.tasklistapp.databinding.ActivityDetailBinding
import com.example.tasklistapp.databinding.ActivityInputMessageBinding
import com.example.tasklistapp.viewmodel.CreateRequestViewModel
import com.example.tasklistapp.viewmodel.DetailViewModel

class InputMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val createRequestViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(CreateRequestViewModel::class.java)

        binding.btnSendMessage.setOnClickListener {
            val message = binding.etMessage.text.toString()
            createRequestViewModel.createRequest(message)
        }

        createRequestViewModel.responseCreate.observe(this) {response ->
            if(response.success) {
                Toast.makeText(this, "Message sent succesffully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to send message: ${response.error}", Toast.LENGTH_SHORT).show()
            }
        }

        createRequestViewModel.isLoadingCreate.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}