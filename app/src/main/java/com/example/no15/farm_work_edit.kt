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
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB WHERE rowid LIKE '${farmid}'",null)
        c.moveToFirst()
        for (i in 0 until c.count) {
            date = "${c.getString(1)}"
            crop = "${c.getString(0)}"
            work = "${c.getString(4)}"
            tips = "${c.getString(5)}"
            c.moveToNext()
        }
        Log.d("dddddddd","${farmid}")
        editText_Crop_edit.setText("${crop}")
        editText_Date_edit.setText("${date}")
        editText_Work_edit.setText("${work}")
        editText_Tips_edit.setText("${tips}")

        button_Farmwork_add_edit.setOnClickListener {
            if (editText_Crop_edit.length()<1 || editText_Date_edit.length()<1 || editText_Work_edit.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "UPDATE FarmWorkDB SET crop = '${editText_Crop_edit.text}',date = '${editText_Date_edit.text}'," +
                                "work = '${editText_Work_edit.text}',tips = '${editText_Tips_edit.text}'," +
                                "code = '${spinner_Code_edit.selectedItem}'," +
                                "number = '${spinner_Number_edit.selectedItem}' WHERE rowid LIKE '${farmid}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, FarmworkData::class.java))
                editText_Crop_edit.setText("")
                editText_Date_edit.setText("")
                editText_Work_edit.setText("")
                editText_Tips_edit.setText("")
            }
        }

        val code = arrayListOf("A","B","C","D","E","F")       //田區代號
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,code)
        spinner_Code_edit.adapter = arrayAdapter
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
        val number_BCDE = arrayListOf("1","2","3","4","5","6","1~6")
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


}
