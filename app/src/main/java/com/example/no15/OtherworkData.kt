package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var id_: ArrayList<String> = ArrayList()
    private var text: ArrayList<String> = ArrayList()
    private var date: ArrayList<String> = ArrayList()
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
                    startActivity(Intent(this,FarmworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)   //收側邊框
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    layout_drawer_Otherwork.closeDrawer(navigation_drawer_Otherwork)
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
            val c = dbrw.rawQuery("SELECT * FROM OtherWorkDB", null)
            c.moveToFirst()
            date.clear()
            text.clear()
            id_.clear()
            for (i in 0 until c.count) {
                text.add("${c.getString(1)}")
                date.add("${c.getString(0)}")
                id_.add("${c.getString(2)}")
                c.moveToNext()
            }
            Toast.makeText(this, "${id_[i]}", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle("日期:${date[i]}")
                .setMessage("內容 :${text[i]}")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
                        AlertDialog.Builder(this)
                            .setTitle("刪除")                                                          //刪除
                            .setMessage("確定刪除紀錄內容?")
                            .setPositiveButton("確定") { dialog, which ->
                                try {
                                    dbrw.execSQL("DELETE FROM OtherWorkDB WHERE id_ LIKE '${id_[i]}'")
                                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                                    val c = dbrw.rawQuery("SELECT * FROM OtherWorkDB", null)
                                    c.moveToFirst()
                                    adapter.notifyDataSetChanged()
                                    startActivity(Intent(this, OtherworkData::class.java))
                                } catch (e: Exception) {
                                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("取消") { dialog, which ->
                                try {
                                    Toast.makeText(this, "cancle", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                                }
                            }.show()
                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("編輯") { dialog, which ->
                    try {
                        Log.d("dddddddd", "${i}")
                        val intent = Intent(this, other_work_edit::class.java)
                        intent.putExtra("id", "${id_[i]}")
                        startActivity(intent)
                        Toast.makeText(this, "編輯", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
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
            items.add("\t\t${c.getString(0)}\n" +
                    "\t\t\t\t\t\t\t\t\t${c.getString(1)}")
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