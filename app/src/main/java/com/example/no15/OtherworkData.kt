package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farmwork_data.*
import kotlinx.android.synthetic.main.activity_farmwork_data.layout_drawer
import kotlinx.android.synthetic.main.activity_muckwork_data.*
import kotlinx.android.synthetic.main.activity_otherwork_data.*
import kotlinx.android.synthetic.main.activity_wormwork_data.*

class OtherworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otherwork_data)

        LV_otherwork.bringToFront()

        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_otherwork.adapter = adapter

        add_record_otherwork.setOnClickListener {
            //紀錄(筆)按鈕
            startActivity(Intent(this, OtherWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar_otherwork_data.setNavigationOnClickListener {
            if (layout_drawer_Otherwork.isDrawerOpen(navigation_drawer_Otherwork)) {            //如果側邊欄是開的就關掉
                layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
                LV_otherwork.bringToFront()
            } else {
                layout_drawer_Otherwork.openDrawer(navigation_drawer_Otherwork)                //如果側邊欄是關的就打開
                layout_drawer_Otherwork.bringToFront()
            }
        }

        navigation_drawer_Otherwork.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
//                    replaceFragement(FarmWork_Data_Fragement())  //切換fragement
                    startActivity(Intent(this,FarmworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)   //收側邊框
//                    LV_farmwork.bringToFront()
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
//                    replaceFragement(Muck_Data_Fragment())
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
//                    LV_muckwork.bringToFront()
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
//                    replaceFragement(Worm_Data_Fragment())
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
//                    LV_wormwork.bringToFront()
                }
                R.id.drawer_other -> {         //其他按鈕被點選
//                    replaceFragement(Other_Data_Fragment())
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
//                    LV_otherwork.bringToFront()
                }
                R.id.drawer_logout -> {      //登出
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else -> {}
            }
            return@setNavigationItemSelectedListener true
        }

        show()

        LV_otherwork.setOnItemClickListener { adapterView, view, i, l ->
            var date = ""
            var text = ""
//            var data = arrayOf("date","crop","work","code","number","tips")
            val click = l
            val search = "SELECT * FROM OtherWorkDB WHERE (rowid-1) LIKE '${click}'"
            val c = dbrw.rawQuery(search, null)
            c.moveToFirst()
            for (i in 0 until c.count) {
                text = "${c.getString(1)}"
                date = "${c.getString(0)}"
                c.moveToNext()
            }

//            Toast.makeText(this, "${l}", Toast.LENGTH_SHORT).show()
//            val view = LayoutInflater.from(this).inflate(R.layout.activity_farm_alert,null)
            AlertDialog.Builder(this)
                .setTitle("日期:${date}")
                .setMessage("內容 :${text}13")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
//                        dbrw.execSQL("DELETE FROM OtherWorkDB WHERE (rowid-1) LIKE '${click}'")
                        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
//                        adapter.notifyDataSetChanged()
//                        startActivity(Intent(this,FarmworkData::class.java))
                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("編輯") { dialog, which ->
                    Toast.makeText(this, "編輯", Toast.LENGTH_SHORT).show()
                }.show()
            adapter.notifyDataSetChanged()
            c.close()
        }
    }

    private fun show(){
        val c = dbrw.rawQuery("SELECT * FROM OtherWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
            items.add("日期:${c.getString(0)}內容:${c.getString(1)}")
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {    //上一頁
        AlertDialog.Builder(this)    //會跳一個提示框
            .setTitle("登出")
            .setMessage("確定登出?")
            .setNegativeButton("取消"){
                    dialog, which->
                Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("登出") {dialog,which ->
                startActivity(Intent(this, MainActivity::class.java)) //案箭頭回上一頁
            }.show()
    }
}