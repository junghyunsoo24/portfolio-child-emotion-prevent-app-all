package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.portfoliochildemotionpreventappall.databinding.ActivityExpertRegisterBinding
import com.example.portfoliochildemotionpreventappall.register.ExpertRegisterApi
import com.example.portfoliochildemotionpreventappall.register.ExpertRegisterData
import kotlinx.coroutines.launch

class ExpertRegisterActivity : AppCompatActivity(){
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var institution: String
    private lateinit var baseUrl: String

    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityExpertRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "전문가 회원 가입"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.expertRegisterBtn.setOnClickListener {
            id = binding.firstExpertIdInput.text.toString()
            pw = binding.firstExpertPwdInput.text.toString()
            name = binding.expertNameInput.text.toString()
            email = binding.emailInput.text.toString()
            institution = binding.institutionInput.text.toString()

            baseUrl = resources.getString(R.string.api_ip_server)
            mobileToServer()
        }
    }

    fun onRegistButtonClicked(){
        val intent = Intent(this, AllLoginActivity::class.java)
        startActivity(intent)
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("회원가입 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            onRegistButtonClicked()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ExpertRegisterData(id, pw, name, email, institution)
                val response = ExpertRegisterApi.retrofitService(baseUrl).sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
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
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }
}