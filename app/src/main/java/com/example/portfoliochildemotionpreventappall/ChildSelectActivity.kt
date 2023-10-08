package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.databinding.ActivityChildSelectBinding

class ChildSelectActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val binding = ActivityChildSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        binding.ChatBotBtn.setOnClickListener {
            onAIButtonClicked()
        }

        binding.ExpertBtn.setOnClickListener {
            onExpertButtonClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myPage_btn -> {
                val intent = Intent(this, AllMyPageActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onAIButtonClicked(){
        val intent = Intent(this, ChildChatBotActivity::class.java)
        startActivity(intent)
    }

    fun onExpertButtonClicked(){
        val intent = Intent(this, ChildExpertChatActivity::class.java)
        startActivity(intent)
    }
}