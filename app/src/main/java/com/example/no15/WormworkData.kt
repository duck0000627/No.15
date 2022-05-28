package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_farmwork_data.*
import kotlinx.android.synthetic.main.activity_wormwork_data.*

class WormworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wormwork_data)

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
            } else {
                layout_drawer_Wormwork.openDrawer(navigation_drawer_Wormwork)                //如果側邊欄是關的就打開
            }
        }

        navigation_drawer_Wormwork.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
//                    replaceFragement(FarmWork_Data_Fragement())  //切換fragement
                    startActivity(Intent(this,FarmworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)   //收側邊框
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
//                    replaceFragement(Muck_Data_Fragment())
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
//                    replaceFragement(Worm_Data_Fragment())
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
//                    replaceFragement(Other_Data_Fragment())
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                }
                R.id.drawer_logout -> {      //登出
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else -> {}
            }
            return@setNavigationItemSelectedListener true
        }

        show()
    }

    private fun show(){
        val c = dbrw.rawQuery("SELECT * FROM WormWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
            items.add("防治對象:${c.getString(0)}名稱:${c.getString(1)}批號:${c.getString(2)}使用量:${c.getString(3)}稀釋倍數:${c.getString(4)}其他稀釋方法:${c.getString(5)}")
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()
        c.close()
    }
}