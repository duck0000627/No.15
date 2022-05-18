package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_farmwork.*

class FarmWork : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmwork)

        toolbar_Farmwork.inflateMenu(R.menu.add_toolbar)   //toolbar樣式載入

        toolbar_Farmwork.setNavigationOnClickListener{
            startActivity(Intent(this,DataPage::class.java))     //案箭頭回上一頁
        }

        ArrayAdapter.createFromResource(this,R.array.Code,android.R.layout.simple_spinner_item).also { adapter ->     //CODE下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_Code.adapter = adapter
        }
        ArrayAdapter.createFromResource(this,R.array.Number,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_Number.adapter = adapter
        }
    }
}