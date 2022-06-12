package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_farm_work_edit.*
import kotlinx.android.synthetic.main.activity_worm_work.*
import kotlinx.android.synthetic.main.activity_worm_work_edit.*

class worm_work_edit : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase
    private var id:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worm_work_edit)

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        intent?.extras?.let {
            val searchid = it.getString("id")
            id = searchid
        }

        var farmid = id
        var who = ""
        var name = ""
        var farmnumber = ""
        var number = ""
        var use = ""
        var multiple = ""
        var other = ""
        var date = ""
        var code = ""
        var crop = ""
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB WHERE id_ LIKE '${farmid}'", null)
        c.moveToFirst()
        for (i in 0 until c.count) {
            name = "${c.getString(11)}"
            who = "${c.getString(10)}"
            multiple = "${c.getString(14)}"
            number = "${c.getString(12)}"
            use = "${c.getString(13)}"
            other = "${c.getString(15)}"
            date = "${c.getString(1)}"
            crop = "${c.getString(0)}"
            code = "${c.getString(2)}"
            farmnumber = "${c.getString(3)}"
            c.moveToNext()
        }
        Log.d("dddddddd","${farmid}")
        editText_WormWho_edit.setText("${who}")
        editText_WormName_edit.setText("${name}")
        editText_WormNumber_edit.setText("${number}")
        editText_WormUse_edit.setText("${use}")
        editText_WormMultiple_edit.setText("${multiple}")
        editText_WormOther_edit.setText("${other}")

        btn_worm_add_edit.setOnClickListener {
            if (editText_WormWho_edit.length()<1 || editText_WormName_edit.length()<1 || editText_WormNumber_edit.length()<1 || editText_WormUse_edit.length()<1 || editText_WormMultiple_edit.length()<1){
                Toast.makeText(this,"請勿留空", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "UPDATE FarmWorkDB SET wormwho = '${editText_WormWho_edit.text}',wormname = '${editText_WormName_edit.text}'," +
                                "wormnumber = '${editText_WormNumber_edit.text}',wormuse = '${editText_WormUse_edit.text}'," +
                                "wormmultiple = '${editText_WormMultiple_edit.text}'," +
                                "wormother = '${editText_WormOther_edit.text}' WHERE id_ LIKE '${farmid}'")
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, WormworkData::class.java))
                editText_WormWho_edit.setText("")
                editText_WormName_edit.setText("")
                editText_WormNumber_edit.setText("")
                editText_WormUse_edit.setText("")
                editText_WormMultiple_edit.setText("")
                editText_WormOther_edit.setText("")
            }
        }
    }
}