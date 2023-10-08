package com.example.portfoliochildemotionpreventappall

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.databinding.ActivityAllMypageBinding

class AllMyPageActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private val sharedPreferencesKey = "chat_history"
    private val sharedPreferencesKey2 = "expert_history"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAllMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "마이 페이지"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        binding.memberId.text = "아이디: " + viewModel.getUserId().value

        binding.logoutBtn.setOnClickListener {
            clearChatHistory()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("로그아웃")
            builder.setMessage("로그아웃 완료")

            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                onLogoutButtonClicked()
            }
            builder.show()
        }
    }

    fun onLogoutButtonClicked(){
        val intent = Intent(this, AllLoginActivity::class.java)
        startActivity(intent)
    }

    private fun clearChatHistory() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val sharedPreferences2 = getSharedPreferences(sharedPreferencesKey2, Context.MODE_PRIVATE)
        val editor2 = sharedPreferences2.edit()

        editor.remove(viewModel.getUserId().value)
        editor.apply()

        editor2.remove(viewModel.getUserId().value)
        editor2.apply()
    }
}