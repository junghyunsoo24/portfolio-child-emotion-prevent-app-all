package com.example.portfoliochildemotionpreventappall

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfoliochildemotionpreventappall.adapter.ChatBotAdapter
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.chatbot.ChatBotApi
import com.example.portfoliochildemotionpreventappall.chatbot.ChatBotData
import com.example.portfoliochildemotionpreventappall.chatbot.ChatBotDataPair
import com.example.portfoliochildemotionpreventappall.databinding.ActivityChildChatbotBinding

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class ChildChatBotActivity : AppCompatActivity() {
    private lateinit var input: String
    private lateinit var id: String

    private lateinit var adapter: ChatBotAdapter
    private val messages = mutableListOf<ChatBotDataPair>()
    private lateinit var binding: ActivityChildChatbotBinding

    private lateinit var viewModel: AppViewModel

    private val sharedPreferencesKey = "chat_history"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

        adapter = ChatBotAdapter(messages)
        binding.chatBotRecyclerView.adapter = adapter
        binding.chatBotRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input = binding.input.text.toString()
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.input.windowToken, 0)
                if (input.isNotBlank()) {
                    mobileToServer()

                    binding.input.text = null
                }
                true
            } else {
                false
            }

        }

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "AI 챗봇"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        loadChatHistory()
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

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ChatBotData(id, input)

                val response = ChatBotApi.retrofitService.sendMessage(message)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseData = responseBody.bot

                        val chatBotDataPair = ChatBotDataPair(input, responseData)
                        messages.add(chatBotDataPair)

                        adapter.notifyDataSetChanged()
                        scrollToBottom()

                        saveChatHistory()
                    } else {
                        Log.e("@@@@Error3", "Response body is null")
                    }
                } else {
                    Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    private fun scrollToBottom() {
        binding.chatBotRecyclerView.post {
            binding.chatBotRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    private fun loadChatHistory() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val chatHistoryJson = sharedPreferences.getString(id, "")

        if (!chatHistoryJson.isNullOrEmpty()) {
            val chatHistory = Gson().fromJson<List<ChatBotDataPair>>(chatHistoryJson, object : TypeToken<List<ChatBotDataPair>>() {}.type)
            messages.addAll(chatHistory)
            adapter.notifyDataSetChanged()
            scrollToBottom()
        }
    }

    private fun saveChatHistory() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val chatHistoryJson = Gson().toJson(messages)
        editor.putString(id, chatHistoryJson)
        editor.apply()
    }

    private fun clearChatHistory() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove(id)
        editor.apply()
    }
}