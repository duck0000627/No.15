package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_farmwork.*

class FarmWork : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmwork)

        toolbar_Farmwork.inflateMenu(R.menu.add_toolbar)   //toolbar樣式載入

        toolbar_Farmwork.setNavigationOnClickListener{
            startActivity(Intent(this,DataPage::class.java))     //案箭頭回上一頁
        }
    }
}