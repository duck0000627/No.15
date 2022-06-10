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

class WormworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wormwork_data)

        LV_wormwork.bringToFront()

        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_wormwork.adapter = adapter

        add_record_wormwork.setOnClickListener {
            //紀錄(筆)按鈕
            startActivity(Intent(this, WormWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar_wormwork_data.setNavigationOnClickListener {
            if (layout_drawer_Wormwork.isDrawerOpen(navigation_drawer_Wormwork)) {            //如果側邊欄是開的就關掉
                layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                LV_wormwork.bringToFront()
            } else {
                layout_drawer_Wormwork.openDrawer(navigation_drawer_Wormwork)                //如果側邊欄是關的就打開
                layout_drawer_Wormwork.bringToFront()
            }
        }

        navigation_drawer_Wormwork.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
                    startActivity(Intent(this,FarmworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)   //收側邊框
//                    LV_farmwork.bringToFront()
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
//                    LV_muckwork.bringToFront()
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
//                    LV_wormwork.bringToFront()
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    startActivity(Intent(this,OtherworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
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

        LV_wormwork.setOnItemClickListener { adapterView, view, i, l ->
            var who = ""
            var name = ""
            var number = ""
            var use = ""
            var multiple = ""
            var other = ""
//            var data = arrayOf("date","crop","work","code","number","tips")
            val click = l
            val search = "SELECT * FROM WormWorkDB WHERE (rowid-1) LIKE '${click}'"
            val c = dbrw.rawQuery(search, null)
            c.moveToFirst()
            for (i in 0 until c.count) {
                name = "${c.getString(1)}"
                who = "${c.getString(0)}"
                multiple = "${c.getString(4)}"
                number = "${c.getString(2)}"
                use = "${c.getString(3)}"
                other = "${c.getString(5)}"
                c.moveToNext()
            }

            Toast.makeText(this, "${l}", Toast.LENGTH_SHORT).show()
//            val view = LayoutInflater.from(this).inflate(R.layout.activity_farm_alert,null)
            AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("防治對象 :${who}\n資材名稱:\t\t\t${name}\n批號 :${number}\n使用量:\t\t\t${use}倍\n稀釋倍數:\t\t\t${multiple}倍\n其他:\t\t\t${other}")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
//                        dbrw.execSQL("DELETE FROM WormWorkDB WHERE (rowid-1) LIKE '${click}'")
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
        val c = dbrw.rawQuery("SELECT * FROM WormWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
            items.add("名稱:${c.getString(1)}\t\t\t批號:${c.getString(2)}\n防治對象:${c.getString(0)}\n使用量:${c.getString(3)}稀釋倍數:${c.getString(4)}\n其他防治方法:${c.getString(5)}")
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()
    }
}