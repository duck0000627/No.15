package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*
import kotlinx.android.synthetic.main.activity_muck_work.*

class MuckWork : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muck_work)

        toolbar_Muckwork.inflateMenu(R.menu.add_toolbar)   //toolbar樣式載入

        toolbar_Muckwork.setNavigationOnClickListener{
            Log.d("Alert","1")
            AlertDialog.Builder(this)    //會跳一個提示框
                .setTitle("捨棄")
                .setMessage("確定捨棄紀錄內容?")
                .setNegativeButton("取消"){
                        dialog, which->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("捨棄") {dialog,which ->
                    startActivity(Intent(this, FarmWork::class.java)) //案箭頭回上一頁
                }.show()
        }

        ArrayAdapter.createFromResource(this,R.array.use_number,android.R.layout.simple_spinner_item).also { adapter ->     //使用量下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_UseNumber.adapter = adapter

        }

        toolbar_Muckwork.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.toolbar_success -> {            //當打勾按下去
                    startActivity(Intent(this,FarmWork::class.java))   //回到上一頁
                }
            }
            false
        }

    }
}