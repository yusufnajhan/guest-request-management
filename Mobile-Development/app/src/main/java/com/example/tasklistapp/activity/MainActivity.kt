package com.example.tasklistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasklistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewList.setOnClickListener {
            val listTaskIntent = Intent(this@MainActivity, ListTaskActivity::class.java)
            startActivity(listTaskIntent)
        }

        binding.viewInput.setOnClickListener {
            val inputMessageIntent = Intent(this@MainActivity, InputMessageActivity::class.java)
            startActivity(inputMessageIntent)
        }
    }
}