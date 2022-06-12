package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_farm_work_edit.*
import kotlinx.android.synthetic.main.activity_other_work_edit.*

class other_work_edit : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase
    private var id:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_work_edit)

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        intent?.extras?.let {
            val searchid = it.getString("id")
            id = searchid
        }

        var farmid = id
        var date = ""
        var text = ""

        val c = dbrw.rawQuery("SELECT * FROM OtherWorkDB WHERE id_ LIKE '${farmid}'",null)
        c.moveToFirst()
        for (i in 0 until c.count) {
            date = "${c.getString(0)}"
            text = "${c.getString(1)}"
            c.moveToNext()
        }

        editText_OtherDate_edit.setText("${date}")
        editText_OtherText_edit.setText("${text}")

        btn_Otherwork_add_edit.setOnClickListener {
            if (editText_OtherDate_edit.length()<1 || editText_OtherText_edit.length()<1){
                Toast.makeText(this,"請勿留空", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL("UPDATE OtherWorkDB SET date = '${editText_OtherDate_edit.text}',text = '${editText_OtherText_edit.text}' WHERE id_ LIKE '${farmid}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, OtherworkData::class.java))
                editText_OtherDate_edit.setText("")
                editText_OtherText_edit.setText("")
            }

        }
        c.close()
    }
    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }
}