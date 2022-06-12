package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_farmwork_data.*
import kotlinx.android.synthetic.main.activity_farmwork_data.layout_drawer
import kotlinx.android.synthetic.main.activity_farmwork_data.navigation_drawer

class FarmworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private var date : ArrayList<String> = ArrayList()
    private var crop : ArrayList<String> = ArrayList()
    private var work : ArrayList<String> = ArrayList()
    private var code : ArrayList<String> = ArrayList()
    private var number : ArrayList<String> = ArrayList()
    private var tips : ArrayList<String> = ArrayList()
    private var id_ : ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmwork_data)

        LV_farmwork.bringToFront()

        Stetho.initializeWithDefaults(this)     //看資料庫的套件程式

        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_farmwork.adapter = adapter

        add_record_farmwork.setOnClickListener {                               //紀錄(筆)按鈕
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar_farmwork_data.setNavigationOnClickListener {

            if (layout_drawer.isDrawerOpen(navigation_drawer)) {            //如果側邊欄是開的就關掉
                layout_drawer.closeDrawer(navigation_drawer)
                LV_farmwork.bringToFront()
            } else {
                layout_drawer.openDrawer(navigation_drawer)                //如果側邊欄是關的就打開
                layout_drawer.bringToFront()
            }
        }

        navigation_drawer.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
                    layout_drawer.closeDrawer(navigation_drawer)   //收側邊框
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    startActivity(Intent(this, MuckworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
//
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this, WormworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_other -> {         //其他按鈕被點選a
                    startActivity(Intent(this, OtherworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_logout -> {      //登出
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else -> {}
            }
            return@setNavigationItemSelectedListener true
        }

        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB", null)            // show()
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count) {
            items.add(
                "農作物:${c.getString(0)}\n\n" +
                        "\t\t\t\t${c.getString(1)}" +
                        "\t\t\t\t\t\t\t\t${c.getString(4)}" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t${c.getString(2)}${c.getString(3)}" +
                        "\t\t\t\t\t\t\t\t\t\t\t${c.getString(5)}"
            )
            c.moveToNext()
        }
        adapter.notifyDataSetChanged()

        LV_farmwork.setOnItemClickListener { adapterView, view, i, l ->                 //點擊顯示

//            val search = "SELECT * FROM FarmWorkDB WHERE id_ = '${click}'"              //get跟listview同位置的資料
//            val c = dbrw.rawQuery(search, null)
            val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB", null)
            c.moveToFirst()
            date.clear()
            crop.clear()
            work.clear()
            code.clear()
            number.clear()
            tips.clear()
            id_.clear()
            for (i in 0 until c.count) {     //c.count = 資料筆數
                date.add("${c.getString(1)}")
                crop.add("${c.getString(0)}")
                work.add("${c.getString(4)}")
                code.add("${c.getString(2)}")
                number.add("${c.getString(3)}")
                tips.add("${c.getString(5)}")
                id_.add("${c.getString(16)}")
                c.moveToNext()
            }

            Toast.makeText(this, "${id_[i]}", Toast.LENGTH_SHORT).show()
            val view = if ("${work[i]}" == "防病蟲害") {       //根據點開的顯示alert的圖片
                LayoutInflater.from(this).inflate(R.layout.activity_worm_alert, null)
            } else if ("${work[i]}" == "除草") {
                LayoutInflater.from(this).inflate(R.layout.grass_alert, null)
            } else if ("${work[i]}" == "施肥") {
                LayoutInflater.from(this).inflate(R.layout.muck_alert, null)
            } else if ("${work[i]}" == "灌溉") {
                LayoutInflater.from(this).inflate(R.layout.water_alert, null)
            } else if ("${work[i]}" == "播種") {
                LayoutInflater.from(this).inflate(R.layout.seed_alert, null)
            } else {
                LayoutInflater.from(this).inflate(R.layout.land_alert, null)
            }
            AlertDialog.Builder(this)      //小框框
                .setTitle("日期:${date[i]}")
                .setView(view)
                .setMessage("${work[i]}\n田區:\t\t\t${code[i]}${number[i]}\n備註:\t\t\t${tips[i]}")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
                        AlertDialog.Builder(this)
                            .setTitle("刪除")                                                          //刪除
                            .setMessage("確定刪除紀錄內容?")
                            .setPositiveButton("確定") { dialog, which ->
                                try {
                                    dbrw.execSQL("DELETE FROM FarmWorkDB WHERE id_ LIKE '${id_[i]}'")
                                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                                    val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB", null)
                                    c.moveToFirst()
                                    adapter.notifyDataSetChanged()
                                    startActivity(Intent(this, FarmworkData::class.java))
                                } catch (e: Exception) {
                                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("取消") { dialog, which ->
                                try {

                                } catch (e: Exception) {

                                }
                            }.show()

                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("編輯") { dialog, which ->                                 //編輯
                    try {
//                        val search = click
                        Log.d("dddddddd", "${id_[i]}")
                        val intent = Intent(this, farm_work_edit::class.java)
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
