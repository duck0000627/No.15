package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_boss.setOnClickListener{
            startActivity(Intent(this, SelectAction::class.java))      //切換頁面
        }

        btn_employee.setOnClickListener{
            startActivity(Intent(this, SelectAction::class.java))      //切換頁面
        }
    }
}