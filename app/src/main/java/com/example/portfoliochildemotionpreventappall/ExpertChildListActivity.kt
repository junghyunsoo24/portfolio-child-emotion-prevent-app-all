package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfoliochildemotionpreventappall.adapter.ExpertChildListAdapter
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.databinding.ActivityExpertChildlistBinding
import com.example.portfoliochildemotionpreventappall.expertChildList.Child
import com.example.portfoliochildemotionpreventappall.expertChildList.ExpertChildListApi
import com.example.portfoliochildemotionpreventappall.expertChildList.ExpertChildListData
import kotlinx.coroutines.launch

class ExpertChildListActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityExpertChildlistBinding
    private lateinit var result: List<Child>
    private lateinit var id: String
    private lateinit var baseUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpertChildlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "할당된아동 목록조회"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

        val layoutManager = LinearLayoutManager(this)
        binding.expertChildListRecyclerView.layoutManager = layoutManager
        val adapter = ExpertChildListAdapter(emptyList()) { child ->
            viewModel.setChildId(child.id)
            onChildChatButtonClicked()
        }
        binding.expertChildListRecyclerView.adapter = adapter

        baseUrl = resources.getString(R.string.api_ip_server)
        mobileToServer()
    }

    fun onChildChatButtonClicked() {
        val intent = Intent(this, ExpertChildChatActivity::class.java)
        startActivity(intent)
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ExpertChildListData(id)
                val response = ExpertChildListApi.retrofitService(baseUrl).sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.result
                        result = responseData

                        val adapter = binding.expertChildListRecyclerView.adapter as ExpertChildListAdapter
                        adapter.expertChildList = result // 어댑터에 데이터 설정
                        adapter.notifyDataSetChanged()


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
}