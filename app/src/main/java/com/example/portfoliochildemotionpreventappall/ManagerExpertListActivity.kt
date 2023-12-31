package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfoliochildemotionpreventappall.adapter.ManagerExpertListAdapter
import com.example.portfoliochildemotionpreventappall.appViewModel.AppViewModel
import com.example.portfoliochildemotionpreventappall.databinding.ActivityManagerExpertlistBinding
import com.example.portfoliochildemotionpreventappall.managerAllocate.AllocateApi
import com.example.portfoliochildemotionpreventappall.managerAllocate.AllocateData
import com.example.portfoliochildemotionpreventappall.managerExpertList.Expert
import com.example.portfoliochildemotionpreventappall.managerExpertList.ManagerExpertListApi
import kotlinx.coroutines.launch

class ManagerExpertListActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityManagerExpertlistBinding
    private lateinit var baseUrl: String

    private lateinit var result: List<Expert>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerExpertlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "전문가 목록"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = AppViewModel.getInstance()

        val layoutManager = LinearLayoutManager(this)
        binding.managerExpertListRecyclerView.layoutManager = layoutManager
        val adapter = ManagerExpertListAdapter(emptyList()) { expert ->
            viewModel.setExpertId(expert.id)
            allocateDataToServer()

        }
        binding.managerExpertListRecyclerView.adapter = adapter

        baseUrl = resources.getString(R.string.api_ip_server)
        mobileToServer()
    }

    private fun allocateDataToServer() {
        lifecycleScope.launch {
            try {
                val message = viewModel.getChildId().value?.let { AllocateData(it,
                    viewModel.getExpertId().value!!
                ) }
                val response = message?.let { AllocateApi.retrofitService(baseUrl).sendsMessage(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        val responseBody = response?.body()
                        if (responseBody != null) {
                            // 서버 응답을 확인하는 작업 수행
                            val responseData = responseBody.result
                            showAlertDialog(responseData)
                        } else {
                            Log.e("@@@@Error3", "Response body is null")
                        }
                    } else {
                        Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                    }
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("아동 할당 성공\n" + "아동: " + viewModel.getChildId().value +
                "\n" + "청소년: " + viewModel.getExpertId().value)

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val response = ManagerExpertListApi.retrofitService(baseUrl).sendsMessage()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.expert
                        result = responseData

                        val adapter =
                            binding.managerExpertListRecyclerView.adapter as ManagerExpertListAdapter
                        adapter.expertList = result // 어댑터에 데이터 설정
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