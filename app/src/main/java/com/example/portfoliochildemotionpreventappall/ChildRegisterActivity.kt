package com.example.portfoliochildemotionpreventappall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.portfoliochildemotionpreventappall.databinding.ActivityChildRegisterBinding
import com.example.portfoliochildemotionpreventappall.register.RegisterApi
import com.example.portfoliochildemotionpreventappall.register.RegisterData

import kotlinx.coroutines.launch

class ChildRegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var age: String
    private lateinit var address: String
    private lateinit var gender: String
    private lateinit var phone_num: String

    private lateinit var genderValue: String
    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityChildRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "아동 회원 가입"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val genders = arrayOf("남", "여")
        val spinner = findViewById<Spinner>(R.id.spinner_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        binding.registBtn.setOnClickListener {
            id = binding.firstIdInput.text.toString()
            pw = binding.firstPwdInput.text.toString()
            name = binding.nameInput.text.toString()
            age = binding.ageInput.text.toString()
            address = binding.addressInput.text.toString()
            gender?.let { selectedGender ->
                genderValue = if (selectedGender == "남") "0" else "1"
            }
            phone_num = binding.addressInput.text.toString()

            mobileToServer()
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: android.view.View?,
        position: Int,
        id: Long
    ) {
        gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun onRegistButtonClicked() {
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
                val message = RegisterData(id, pw, name, age, address, genderValue, phone_num)
                val response = RegisterApi.retrofitService.sendsMessage(message)
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