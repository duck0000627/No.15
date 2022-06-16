package com.example.no15

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
import kotlinx.android.synthetic.main.activity_farm_work_edit.*

class farm_work_edit : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase
    private var id:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_work_edit)

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        intent?.extras?.let {
            val searchid = it.getString("id")
            id = searchid
        }

        var farmid = id
        var date = ""
        var crop = ""
        var work = ""
        var tips = ""
        var Code = ""
        var number = ""
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB WHERE rowid LIKE '${farmid}'",null)
        c.moveToFirst()
        for (i in 0 until c.count) {
            date = "${c.getString(1)}"
            crop = "${c.getString(0)}"
            work = "${c.getString(4)}"
            tips = "${c.getString(5)}"
            Code = "${c.getString(2)}"
            number = "${c.getString(3)}"
            c.moveToNext()
        }
        Log.d("dddddddd","${farmid}")
        editText_Date_edit.setText("${date}")
        editText_Work_edit.setText("${work}")
        editText_Tips_edit.setText("${tips}")

        val croparray = arrayListOf("黑豆","黃豆")       //田區代號
        val arrayAdapter_crop = ArrayAdapter(this,android.R.layout.simple_spinner_item,croparray)
        spinner_Crop_edit.adapter = arrayAdapter_crop
        if ("${crop}" == "黑豆")
        {
            spinner_Crop_edit.setSelection(0,true)
        }
        if ("${crop}" == "黃豆")
        {
            spinner_Crop_edit.setSelection(1,true)
        }
        spinner_Crop_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        button_Farmwork_add_edit.setOnClickListener {
            if (editText_Date_edit.length()<1 || editText_Work_edit.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "UPDATE FarmWorkDB SET crop = '${spinner_Crop_edit.selectedItem}',date = '${editText_Date_edit.text}'," +
                                "work = '${editText_Work_edit.text}',tips = '${editText_Tips_edit.text}'," +
                                "code = '${spinner_Code_edit.selectedItem}'," +
                                "number = '${spinner_Number_edit.selectedItem}' WHERE rowid LIKE '${farmid}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, FarmworkData::class.java))
                editText_Date_edit.setText("")
                editText_Work_edit.setText("")
                editText_Tips_edit.setText("")
            }
        }

        val code = arrayListOf("A","B","C","D","E","F")       //田區代號
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,code)
        spinner_Code_edit.adapter = arrayAdapter
        if ("${Code}" == "A")
        {
            spinner_Code_edit.setSelection(0,true)
            val number_A = arrayListOf("1","2","1~2")
            val arrayAdapter_A = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_A)
            spinner_Number_edit.adapter = arrayAdapter_A
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "1~2")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            spinner_Number_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
        if ("${Code}" == "B")                                    //判斷值更改下拉框預設值
        {
            spinner_Code_edit.setSelection(1,true)
            val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
            val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
            spinner_Number_edit.adapter = arrayAdapter_BCDE
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "3")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            if ("${number}" == "4")
            {
                spinner_Number_edit.setSelection(3,true)
            }
            if ("${number}" == "5")
            {
                spinner_Number_edit.setSelection(4,true)
            }
            if ("${number}" == "6")
            {
                spinner_Number_edit.setSelection(5,true)
            }
            if ("${number}" == "1~3")
            {
                spinner_Number_edit.setSelection(6,true)
            }
            if ("${number}" == "3~6")
            {
                spinner_Number_edit.setSelection(7,true)
            }
            if ("${number}" == "1~6")
            {
                spinner_Number_edit.setSelection(8,true)
            }
        }
        else if ("${Code}" == "C")
        {
            spinner_Code_edit.setSelection(2,true)
            val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
            val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
            spinner_Number_edit.adapter = arrayAdapter_BCDE
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "3")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            if ("${number}" == "4")
            {
                spinner_Number_edit.setSelection(3,true)
            }
            if ("${number}" == "5")
            {
                spinner_Number_edit.setSelection(4,true)
            }
            if ("${number}" == "6")
            {
                spinner_Number_edit.setSelection(5,true)
            }
            if ("${number}" == "1~3")
            {
                spinner_Number_edit.setSelection(6,true)
            }
            if ("${number}" == "3~6")
            {
                spinner_Number_edit.setSelection(7,true)
            }
            if ("${number}" == "1~6")
            {
                spinner_Number_edit.setSelection(8,true)
            }
        }
        else if ("${Code}" == "D")
        {
            spinner_Code_edit.setSelection(3,true)
            val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
            val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
            spinner_Number_edit.adapter = arrayAdapter_BCDE
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "3")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            if ("${number}" == "4")
            {
                spinner_Number_edit.setSelection(3,true)
            }
            if ("${number}" == "5")
            {
                spinner_Number_edit.setSelection(4,true)
            }
            if ("${number}" == "6")
            {
                spinner_Number_edit.setSelection(5,true)
            }
            if ("${number}" == "1~3")
            {
                spinner_Number_edit.setSelection(6,true)
            }
            if ("${number}" == "3~6")
            {
                spinner_Number_edit.setSelection(7,true)
            }
            if ("${number}" == "1~6")
            {
                spinner_Number_edit.setSelection(8,true)
            }
        }
        else if ("${Code}" == "E")
        {
            spinner_Code_edit.setSelection(4,true)
            val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
            val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
            spinner_Number_edit.adapter = arrayAdapter_BCDE
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "3")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            if ("${number}" == "4")
            {
                spinner_Number_edit.setSelection(3,true)
            }
            if ("${number}" == "5")
            {
                spinner_Number_edit.setSelection(4,true)
            }
            if ("${number}" == "6")
            {
                spinner_Number_edit.setSelection(5,true)
            }
            if ("${number}" == "1~3")
            {
                spinner_Number_edit.setSelection(6,true)
            }
            if ("${number}" == "3~6")
            {
                spinner_Number_edit.setSelection(7,true)
            }
            if ("${number}" == "1~6")
            {
                spinner_Number_edit.setSelection(8,true)
            }
        }
        else if("${Code}" == "F")
        {
            spinner_Code_edit.setSelection(5,true)
            val number_F = arrayListOf("1","2","3","1~3")
            val arrayAdapter_F = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_F)
            spinner_Number_edit.adapter = arrayAdapter_F
            if ("${number}" == "1")
            {
                spinner_Number_edit.setSelection(0,true)
            }
            if ("${number}" == "2")
            {
                spinner_Number_edit.setSelection(1,true)
            }
            if ("${number}" == "3")
            {
                spinner_Number_edit.setSelection(2,true)
            }
            if ("${number}" == "1~3")
            {
                spinner_Number_edit.setSelection(3,true)
            }
            spinner_Number_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Toast.makeText(this@farm_work_edit,number_F[p2], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
        spinner_Code_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{         //下拉框被選之後
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
        c.close()
    }

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }

    private fun A(){                             //選到A時的函式
        val number_A = arrayListOf("1","2","1~2")
        val arrayAdapter_A = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_A)
        spinner_Number_edit.adapter = arrayAdapter_A
        spinner_Number_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun F(){                                //選到F時的函式
        val number_F = arrayListOf("1","2","3","1~3")
        val arrayAdapter_F = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_F)
        spinner_Number_edit.adapter = arrayAdapter_F
        spinner_Number_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@farm_work_edit,number_F[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun BCDE(){                                         //選到BCDE時的函式
        val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
        val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
        spinner_Number_edit.adapter = arrayAdapter_BCDE
        spinner_Number_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@farm_work_edit,number_BCDE[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onBackPressed() {    //上一頁
        AlertDialog.Builder(this)    //會跳一個提示框
            .setTitle("捨棄")
            .setMessage("確定捨棄修改內容?")
            .setNegativeButton("取消"){
                    dialog, which->
                Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("捨棄") {dialog,which ->
                startActivity(Intent(this, FarmworkData::class.java)) //案箭頭回上一頁
            }.show()
    }

}
