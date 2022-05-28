package com.example.no15

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_farmwork_data.*
import kotlinx.android.synthetic.main.farmwork_data_fragement.*


class FarmworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmwork_data)

        Stetho.initializeWithDefaults(this)     //看資料庫的套件程式

        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_farmwork.adapter = adapter

        add_record_farmwork.setOnClickListener {
            //紀錄(筆)按鈕
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar_farmwork_data.setNavigationOnClickListener {
            if (layout_drawer.isDrawerOpen(navigation_drawer)) {            //如果側邊欄是開的就關掉
                layout_drawer.closeDrawer(navigation_drawer)
            } else {
                layout_drawer.openDrawer(navigation_drawer)                //如果側邊欄是關的就打開
            }
        }

        navigation_drawer.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
//                    replaceFragement(FarmWork_Data_Fragement())  //切換fragement
                    layout_drawer.closeDrawer(navigation_drawer)   //收側邊框
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
//                    replaceFragement(Muck_Data_Fragment())
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
//                    replaceFragement(Worm_Data_Fragment())
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
//                    replaceFragement(Other_Data_Fragment())
                    layout_drawer.closeDrawer(navigation_drawer)
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
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
            items.add("農作物:${c.getString(0)}日期:${c.getString(1)}工作項目:${c.getString(5)}田園代號:${c.getString(3)}${c.getString(4)}備註:${c.getString(5)}")
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()
        c.close()
    }





//    private fun replaceFragement(fragment: Fragment) {     //切換fragement的函式
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.main_fragement, fragment)
//        fragmentTransaction.commit()
//    }

}
