package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_farmwork_data.*
import kotlinx.android.synthetic.main.activity_farmwork_data.layout_drawer
import kotlinx.android.synthetic.main.activity_farmwork_data.navigation_drawer
import kotlinx.android.synthetic.main.activity_muckwork_data.*
import kotlinx.android.synthetic.main.activity_otherwork_data.*
import kotlinx.android.synthetic.main.activity_wormwork_data.*

class MuckworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muckwork_data)


        dbrw = MyDBHelper(this).writableDatabase  //呼叫資料庫
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_muckwork.adapter = adapter

        add_record_muckwork.setOnClickListener {
            //紀錄(筆)按鈕
            startActivity(Intent(this, MuckWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar_muckwork_data.setNavigationOnClickListener {
            if (layout_drawer.isDrawerOpen(navigation_drawer)) {            //如果側邊欄是開的就關掉
                layout_drawer.closeDrawer(navigation_drawer)
                LV_muckwork.bringToFront()
            } else {
                layout_drawer.openDrawer(navigation_drawer)                //如果側邊欄是關的就打開
                layout_drawer.bringToFront()
            }
        }

        navigation_drawer.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
                    startActivity(Intent(this,FarmworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)   //收側邊框
//                    LV_farmwork.bringToFront()
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    layout_drawer.closeDrawer(navigation_drawer)
//                    LV_muckwork.bringToFront()
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
//                    LV_wormwork.bringToFront()
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    startActivity(Intent(this,OtherworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
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

        LV_muckwork.setOnItemClickListener { adapterView, view, i, l ->
            var muckname = ""
            var type = ""
            var counttype = ""
            var count = ""
//            var data = arrayOf("date","crop","work","code","number","tips")
            val click = l
            val search = "SELECT * FROM MuckWorkDB WHERE (rowid-1) LIKE '${click}'"
            val c = dbrw.rawQuery(search, null)
            c.moveToFirst()
            for (i in 0 until c.count) {
                muckname = "${c.getString(1)}"
                type = "${c.getString(0)}"
                counttype = "${c.getString(3)}"
                count = "${c.getString(2)}"
                c.moveToNext()
            }

            Toast.makeText(this, "${l}", Toast.LENGTH_SHORT).show()
//            val view = LayoutInflater.from(this).inflate(R.layout.activity_farm_alert,null)
            AlertDialog.Builder(this)
                .setTitle("施肥別 :${type}")
                .setMessage("名稱 :${muckname}\n使用量 :\t\t\t${count}${counttype}")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
                        dbrw.execSQL("DELETE FROM MuckWorkDB WHERE (rowid-1) LIKE '${click}'")
                        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                        adapter.notifyDataSetChanged()
                        startActivity(Intent(this,FarmworkData::class.java))
                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("編輯") { dialog, which ->
                    Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                }.show()
            adapter.notifyDataSetChanged()
            c.close()
        }
    }

    private fun show(){
        val c = dbrw.rawQuery("SELECT * FROM MuckWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
            items.add("名稱:${c.getString(1)}使用量:${c.getString(2)}${c.getString(3)}")
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()
    }

}