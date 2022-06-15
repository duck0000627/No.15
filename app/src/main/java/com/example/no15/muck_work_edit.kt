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
import kotlinx.android.synthetic.main.activity_farm_work_edit.*
import kotlinx.android.synthetic.main.activity_muck_work.*
import kotlinx.android.synthetic.main.activity_muck_work_edit.*

class muck_work_edit : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase
    private var id:String? = ""
    private var mucktypeselect:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muck_work_edit)

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        intent?.extras?.let {
            val searchid = it.getString("id")
            id = searchid
        }

        ArrayAdapter.createFromResource(this,R.array.use_number,android.R.layout.simple_spinner_item).also { adapter ->     //使用量下拉框
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_UseNumber_edit.adapter = adapter
        }

        var farmid = id
        var muckname = ""
        var type = ""
        var counttype = ""
        var count = ""
        var date = ""
        var code = ""
        var number = ""
        var crop = ""
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB WHERE id_ LIKE '${farmid}'", null)
        c.moveToFirst()
        c.moveToFirst()
        for (i in 0 until c.count) {
            muckname = "${c.getString(7)}"
            type = "${c.getString(6)}"
            counttype = "${c.getString(9)}"
            count = "${c.getString(8)}"
            date = "${c.getString(1)}"
            code = "${c.getString(2)}"
            number = "${c.getString(3)}"
            crop = "${c.getString(0)}"
            c.moveToNext()
        }
        Log.d("dddddddd","${farmid}")
        editText_UseNumber_edit.setText("${count}")

        val mucknamearray = arrayListOf("純德排肥夠多633","大蘋果8號")       //肥料名稱
        val arrayAdapter_muckname = ArrayAdapter(this,android.R.layout.simple_spinner_item,mucknamearray)
        spinner_muckname_edit.adapter = arrayAdapter_muckname
        if (muckname == "純德排肥夠多633")
        {
            spinner_muckname_edit.setSelection(0,true)
        }
        if (muckname == "大蘋果8號")
        {
            spinner_muckname_edit.setSelection(1,true)
        }
        spinner_muckname_edit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        btn_Muckwork_add_edit.setOnClickListener {
            if (editText_UseNumber_edit.length()<1){
                Toast.makeText(this,"請勿留空", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    mucktypeselect = when{               //讀radiobutton的字
                        rdb_basic_edit.isChecked -> "基肥"
                        else -> "追肥"
                    }
                    dbrw.execSQL(
                        "UPDATE FarmWorkDB SET mucktype = '${mucktypeselect}',muckname = '${spinner_muckname_edit.selectedItem}'," +
                                "muckcount = '${editText_UseNumber_edit.text}',muckcounttype = '${spinner_UseNumber_edit.selectedItem}' WHERE id_ LIKE '${farmid}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, MuckworkData::class.java))
                editText_UseNumber_edit.setText("")
            }
        }
        c.close()
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
                startActivity(Intent(this, MuckworkData::class.java)) //案箭頭回上一頁
            }.show()
    }
}