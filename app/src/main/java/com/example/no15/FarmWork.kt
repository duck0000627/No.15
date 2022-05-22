package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*

class FarmWork : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_work)

        toolbar_Farmwork.inflateMenu(R.menu.add_toolbar)   //toolbar樣式載入

        toolbar_Farmwork.setNavigationOnClickListener{
            Log.d("Alert","1")
            AlertDialog.Builder(this)    //會跳一個提示框
                .setTitle("捨棄")
                .setMessage("確定捨棄紀錄內容?")
                .setNegativeButton("取消"){
                    dialog, which->
                    Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("捨棄") {dialog,which ->
                    startActivity(Intent(this, DataPage::class.java)) //案箭頭回上一頁
                }.show()
        }

        ArrayAdapter.createFromResource(this,R.array.Code,android.R.layout.simple_spinner_item).also { adapter ->     //CODE下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_Code.adapter = adapter

        }
        ArrayAdapter.createFromResource(this,R.array.Number,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_Number.adapter = adapter
        }

        button_to_muck.setOnClickListener{
            startActivity(Intent(this,MuckWork::class.java))
        }

        button_to_worm.setOnClickListener{
            startActivity(Intent(this,WormWork::class.java))
        }

    }
}