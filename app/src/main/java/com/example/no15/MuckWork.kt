package com.example.no15

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
    private var E:String? = ""
    private var F:String? = ""
    private var G:String? = ""
    private var H:String? = ""
    private var I:String? = ""
    private var J:String? = ""
    private var Crop:String? = ""
    private var Date:String? = ""
    private var Code:String? = ""
    private var Number:String? = ""
    private var Work:String? = ""
    private var Tips:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muck_work)

        intent?.extras?.let {
            val e = it.getString("who")
            val f = it.getString("name")
            val g = it.getString("use")
            val h = it.getString("multiple")
            val i = it.getString("other")
            val j = it.getString("number")
            val crop = it.getString("crop")
            val date = it.getString("date")
            val code = it.getString("code")
            val number = it.getString("number")
            val work = it.getString("work")
            val tips = it.getString("tips")
            E = e
            F = f
            G = g
            H = h
            I = i
            J = j
            Crop = crop
            Number = number
            Date = date
            Code = code
            Work = work
            Tips = tips
        }

        ArrayAdapter.createFromResource(this,R.array.use_number,android.R.layout.simple_spinner_item).also { adapter ->     //使用量下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_UseNumber.adapter = adapter
        }

        val mucknamearray = arrayListOf("純德排肥夠多633","大蘋果8號")       //肥料名稱
        val arrayAdapter_muckname = ArrayAdapter(this,android.R.layout.simple_spinner_item,mucknamearray)
        spinner_muckname.adapter = arrayAdapter_muckname
        spinner_muckname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        btn_Muckwork_add.setOnClickListener {
            if (editText_UseNumber.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    mucktypeselect = when{               //讀radiobutton的字
                        rdb_basic.isChecked -> "基肥"
                        else -> "追肥"
                    }
                    muckname = spinner_muckname.selectedItem.toString()
                    count = editText_UseNumber.text.toString()
                    counttype = spinner_UseNumber.selectedItem.toString()
                    val bundle = Bundle()                           //帶資料
                    bundle.putString("mucktype","${mucktypeselect}")
                    bundle.putString("muckname","${muckname}")
                    bundle.putString("count","${count}")
                    bundle.putString("counttype","${counttype}")
                    bundle.putString("who","${E}")
                    bundle.putString("name","${F}")
                    bundle.putString("number","${G}")
                    bundle.putString("use","${H}")
                    bundle.putString("multiple","${I}")
                    bundle.putString("other","${J}")
                    bundle.putString("crop","${Crop}")
                    bundle.putString("code","${Code}")
                    bundle.putString("number","${Number}")
                    bundle.putString("tips","${Tips}")
                    bundle.putString("work","${Work}")
                    bundle.putString("date","${Date}")
                    val intent = Intent(this,FarmWork::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    editText_UseNumber.setText("")
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onBackPressed() {                  //返回箭頭
        AlertDialog.Builder(this)    //會跳一個提示框
                .setTitle("捨棄")
                .setMessage("確定捨棄紀錄內容?")
                .setNegativeButton("取消"){
                        dialog, which->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("捨棄") {dialog,which ->
                    val bundle = Bundle()                           //帶資料
                    bundle.putString("crop","${Crop}")
                    bundle.putString("code","${Code}")
                    bundle.putString("number","${Number}")
                    bundle.putString("tips","${Tips}")
                    bundle.putString("work","${Work}")
                    bundle.putString("date","${Date}")
                    val intent = Intent(this,FarmWork::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }.show()
    }

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }


}