package com.example.no15

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*
import kotlinx.android.synthetic.main.activity_muck_work.*
import java.util.*

class MuckWork : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase

    private var mucktypeselect:String = ""
    private var muckname:String = ""
    private var count:String = ""
    private var counttype:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muck_work)

        ArrayAdapter.createFromResource(this,R.array.use_number,android.R.layout.simple_spinner_item).also { adapter ->     //使用量下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_UseNumber.adapter = adapter
        }

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        btn_Muckwork_add.setOnClickListener {
            if (editText_MuckName.length()<1 || editText_UseNumber.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    mucktypeselect = when{               //讀radiobutton的字
                        rdb_basic.isChecked -> "基肥"
                        else -> "追肥"
                    }
//                    dbrw.execSQL("INSERT INTO MuckWorkDB(type,muckname,count,counttype) VALUES(?,?,?,?)",
//                        arrayOf(
//                            mucktype,
//                            editText_MuckName.text.toString(),
//                            editText_UseNumber.text.toString(),
//                            spinner_UseNumber.selectedItem.toString()
//                        )
//                    )
                    //data.mucktype = this.mucktypeselect
                    muckname = editText_MuckName.text.toString()
                    count = editText_UseNumber.text.toString()
                    counttype = spinner_UseNumber.selectedItem.toString()
                    val bundle = Bundle()
                    bundle.putString("mucktype","${mucktypeselect}")
                    bundle.putString("muckname","${muckname}")
                    bundle.putString("count","${count}")
                    bundle.putString("counttype","${counttype}")
                    val intent = Intent(this,FarmWork::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    editText_MuckName.setText("")
                    editText_UseNumber.setText("")
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onBackPressed() {
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

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }


}