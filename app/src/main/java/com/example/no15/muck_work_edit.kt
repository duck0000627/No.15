package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
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
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB WHERE rowid LIKE '${farmid}'", null)
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
        editText_MuckName_edit.setText("${muckname}")
        editText_UseNumber_edit.setText("${count}")

        btn_Muckwork_add_edit.setOnClickListener {
            if (editText_MuckName_edit.length()<1 || editText_UseNumber_edit.length()<1){
                Toast.makeText(this,"請勿留空", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    mucktypeselect = when{               //讀radiobutton的字
                        rdb_basic_edit.isChecked -> "基肥"
                        else -> "追肥"
                    }
                    dbrw.execSQL(
                        "UPDATE FarmWorkDB SET mucktype = '${mucktypeselect}',muckname = '${editText_MuckName_edit.text}'," +
                                "muckcount = '${editText_UseNumber_edit.text}',muckcounttype = '${spinner_UseNumber_edit.selectedItem}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, FarmworkData::class.java))
                editText_MuckName_edit.setText("")
                editText_UseNumber_edit.setText("")
            }
        }
        c.close()
    }
}