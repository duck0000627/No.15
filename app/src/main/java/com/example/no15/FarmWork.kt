package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*

class FarmWork : AppCompatActivity() {


    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_work)

        toolbar_Farmwork.setNavigationOnClickListener{
            //當返回的箭頭被點選
            AlertDialog.Builder(this)    //會跳一個提示框
                .setTitle("捨棄")
                .setMessage("確定捨棄紀錄內容?")
                .setNegativeButton("取消"){
                    dialog, which->
                    Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("捨棄") {dialog,which ->
                    startActivity(Intent(this, FarmworkData::class.java)) //案箭頭回上一頁
                }.show()
        }

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫
        addListener()                                       //新增資料


        val code = arrayListOf("A","B","C","D","E","F")       //田區代號
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,code)
        spinner_Code.adapter = arrayAdapter
        spinner_Code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{         //下拉框被選之後
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (code[p2] == "A"){
                    A()
                }
                if (code[p2] == "F"){
                    F()
                }
                if (code[p2] == "B" || code[p2] == "C" || code[p2] == "D" || code[p2] == "E"){
                    BCDE()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        button_to_muck.setOnClickListener{
            startActivity(Intent(this,MuckWork::class.java))       //肥料使用紀錄按鈕
        }
        button_to_worm.setOnClickListener{
            startActivity(Intent(this,WormWork::class.java))       //防治使用紀錄按鈕
        }


    }

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }

    private fun addListener() {         //新增資料
        button_Farmwork_add.setOnClickListener {
            if (editText_Crop.length()<1 || editText_Date.length()<1 || editText_Work.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "INSERT INTO FarmWorkDB(crop,date,code,number,work,tips) VALUES(?,?,?,?,?,?)",
                        arrayOf(
                            editText_Crop.text.toString(),
                            editText_Date.text.toString(),
                            spinner_Code.selectedItem.toString(),
                            spinner_Number.selectedItem.toString(),
                            editText_Work.text.toString(),
                            editText_Tips.text.toString()
                        )
                    )
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, FarmworkData::class.java))
                editText_Crop.setText("")
                editText_Date.setText("")
                editText_Work.setText("")
                editText_Tips.setText("")
            }
        }
    }



    private fun A(){                             //選到A時的函式
        val number_A = arrayListOf("1","2")
        val arrayAdapter_A = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_A)
        spinner_Number.adapter = arrayAdapter_A
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun F(){                                //選到F時的函式
        val number_F = arrayListOf("1","2","3")
        val arrayAdapter_F = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_F)
        spinner_Number.adapter = arrayAdapter_F
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@FarmWork,number_F[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun BCDE(){                                         //選到BCDE時的函式
        val number_BCDE = arrayListOf("1","2","3","4","5","6")
        val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
        spinner_Number.adapter = arrayAdapter_BCDE
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@FarmWork,number_BCDE[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

}