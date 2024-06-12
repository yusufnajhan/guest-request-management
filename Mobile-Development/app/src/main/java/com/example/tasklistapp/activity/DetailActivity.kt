package com.example.tasklistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasklistapp.R
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.databinding.ActivityDetailBinding
import com.example.tasklistapp.viewmodel.DetailViewModel
import com.example.tasklistapp.viewmodel.UpdateRequestViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val updateRequestViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(UpdateRequestViewModel::class.java)


        val idGet = intent.getIntExtra(ID, 0)
        detailViewModel.findDetailTask(idGet)

        detailViewModel.detailTask.observe(this) { detailTask ->
            setDetailTask(detailTask)
            when (detailTask.status) {
                "new" -> {
                    binding.btnChangeStatus.setOnClickListener{
                        updateRequestViewModel.updateRequestStatus(idGet, "on progress")
                    }
                }
                "on progress" -> {
                    binding.btnChangeStatus.setOnClickListener{
                        updateRequestViewModel.updateRequestStatus(idGet, "completed")
                    }
                }
            }
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }

        updateRequestViewModel.responseUpdate.observe(this) {response ->
            if(response.success) {
                Toast.makeText(this, "Status updated succesffully", Toast.LENGTH_SHORT).show()
                detailViewModel.findDetailTask(idGet)
            } else {
                Toast.makeText(this, "Failed to update status: ${response.error}", Toast.LENGTH_SHORT).show()
            }
        }

        updateRequestViewModel.isLoadingUpdate.observe(this) {
            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailTask(taskDetail: RequestResponseItem) {
        binding.tvTitle.text = buildString {
            append("TASK #")
            append(taskDetail.id)
        }
        binding.tvMessage.text = taskDetail.message
        binding.tvDate.text = taskDetail.createdAt
        binding.tvStatus.text = buildString {
            append("Status: ")
            append(taskDetail.status)
        }
        when (taskDetail.status) {
            "new" -> {
                binding.btnChangeStatus.text = "PROGRESS THIS TASK"
                binding.btnChangeStatus.setOnClickListener{
                }
            }
            "on progress" -> {
                binding.btnChangeStatus.text = "COMPLETE THIS TASK"
            }
            "completed" -> {
                binding.btnChangeStatus.text = "THIS TASK IS COMPLETED"
                binding.btnChangeStatus.isEnabled = false
            }
        }
    }


    companion object {
        const val ID = "od"
    }
}