package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.databinding.ActivityAllMainBinding

class AllMainActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel.getInstance()

        val binding = ActivityAllMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.childBtn.setOnClickListener {
            viewModel.setUser("0")
            onStartButtonClicked()
        }

        binding.expertBtn.setOnClickListener {
            viewModel.setUser("1")
            onStartButtonClicked()
        }

        binding.managerBtn.setOnClickListener {
            viewModel.setUser("2")
            onStartButtonClicked()
        }

    }

    fun onStartButtonClicked() {
        val intent = Intent(this, AllLoginActivity::class.java)
        startActivity(intent)
    }

}