package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*
import kotlinx.android.synthetic.main.activity_muck_work.*
import kotlinx.android.synthetic.main.activity_other_work.*

class OtherWork : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_work)

        toolbar_Otherwork.setNavigationOnClickListener{
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

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫
        addListener()                                       //新增資料

    }

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }

    private fun addListener() {         //新增資料
        btn_Otherwork_add.setOnClickListener {
            if (editText_OtherDate.length()<1 || editText_OtherText.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "INSERT INTO OtherWorkDB(date,text) VALUES(?,?)",
                        arrayOf(
                            editText_OtherDate.text.toString(),
                            editText_OtherText.text.toString()
                        )
                    )
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, OtherworkData::class.java))
                editText_OtherDate.setText("")
                editText_OtherText.setText("")
            }
        }
    }
}