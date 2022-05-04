package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_alert.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_boss.setOnClickListener {
            loginalert_boss()   //老闆登入驗證
        }


        btn_employee.setOnClickListener {
            loginalert_imployee()    //員工登入驗證
        }
    }

    private fun loginalert_boss() {        //老闆登入驗證程式
        val item = LayoutInflater.from(this).inflate(R.layout.login_alert, null)

        AlertDialog.Builder(this)
            .setTitle("請輸入密碼")
            .setView(item)
            .setPositiveButton("確定"){_,_ ->
                val editText = item.password_edidtext as EditText
                val password = editText.text.toString()
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(applicationContext, "請輸入密碼", Toast.LENGTH_SHORT).show()
                }else{
                    if (password == "123456")     //密碼
                        startActivity(Intent(this, DataPage::class.java))      //切換頁面
                    else{
                        Toast.makeText(this,"密碼錯誤", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

    private fun loginalert_imployee() {        //員工登入驗證程式
        val item = LayoutInflater.from(this).inflate(R.layout.login_alert, null)

        AlertDialog.Builder(this)
            .setTitle("請輸入密碼")
            .setView(item)
            .setPositiveButton("確定"){_,_ ->
                val editText = item.password_edidtext as EditText
                val password = editText.text.toString()
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(applicationContext, "請輸入密碼", Toast.LENGTH_SHORT).show()
                }else{
                    if (password == "888888")      //密碼
                        startActivity(Intent(this, DataPage::class.java))      //切換頁面
                    else{
                        Toast.makeText(this,"密碼錯誤", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }
}