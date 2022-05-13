package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_boss_login.*

class Boss_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_login)

        Boss_login.setOnClickListener{
            login()
        }

        toolbar_BossLogin.setNavigationOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun login(){    //登入程式
        var account = BossAccount.text.toString()
        var password = BossPassword.text.toString()
        if (account == "123456" && password == "123456") {    //帳號密碼設定
            startActivity(Intent(this, DataPage::class.java))
        }
        else{
            Toast.makeText(this,"帳密錯誤", Toast.LENGTH_SHORT).show()
        }
    }
}