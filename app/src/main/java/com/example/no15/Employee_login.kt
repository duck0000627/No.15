package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.no15.Employee_login
import kotlinx.android.synthetic.main.activity_boss_login.*
import kotlinx.android.synthetic.main.activity_employee_login.*

class Employee_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)

        Employee_login.setOnClickListener{
            login()
        }

        toolbar_EmployeeLogin.setNavigationOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun login(){    //登入程式
        var account = EmployeeAccount.text.toString()
        var password = EmployeePassword.text.toString()
        if (account == "888888" && password == "888888") {    //帳號密碼設定
            startActivity(Intent(this, DataPage::class.java))
        }
        else{
            Toast.makeText(this,"帳密錯誤", Toast.LENGTH_SHORT).show()
        }
    }
}