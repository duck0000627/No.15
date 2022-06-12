package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_wormwork_data.*

class WormworkData : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private var id_: ArrayList<String> = ArrayList()
    private var who: ArrayList<String> = ArrayList()
    private var farmnumber: ArrayList<String> = ArrayList()
    private var name: ArrayList<String> = ArrayList()
    private var number: ArrayList<String> = ArrayList()
    private var use: ArrayList<String> = ArrayList()
    private var multiple: ArrayList<String> = ArrayList()
    private var other: ArrayList<String> = ArrayList()
    private var date: ArrayList<String> = ArrayList()
    private var code: ArrayList<String> = ArrayList()
    private var crop: ArrayList<String> = ArrayList()
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
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換到新增頁面
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
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    startActivity(Intent(this,MuckworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer_Wormwork.closeDrawer(navigation_drawer_Wormwork)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    startActivity(Intent(this,OtherworkData::class.java))
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

        LV_wormwork.setOnItemClickListener { adapterView, view, i, l ->                    //被點選後
            val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB", null)
            c.moveToFirst()                                                 //移到第一筆資料
            id_.clear()                 //清空array
            who.clear()
            name.clear()
            farmnumber.clear()
            number.clear()
            use.clear()
            multiple.clear()
            other.clear()
            date.clear()
            code.clear()
            crop.clear()
            for (i in 0 until c.count) {      //把資料丟到array
                name.add("${c.getString(11)}")
                who.add("${c.getString(10)}")
                multiple.add("${c.getString(14)}")
                number.add("${c.getString(12)}")
                use.add("${c.getString(13)}")
                other.add("${c.getString(15)}")
                date.add("${c.getString(1)}")
                crop.add("${c.getString(0)}")
                code.add("${c.getString(2)}")
                farmnumber.add("${c.getString(3)}")
                id_.add("${c.getString(16)}")
                c.moveToNext()           //下一筆資料
            }
//            Toast.makeText(this, "${id_[i]}", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)       //小框框
                .setTitle("${date[i]}")
                .setMessage("田區:\t\t\t${code[i]}${farmnumber[i]}\n" +
                        "農作物:\t\t\t${crop[i]}\n" +
                        "防治對象 :\t\t\t${who[i]}\n" +
                        "資材名稱:\t\t\t${name[i]}\n" +
                        "批號 :\t\t\t${number[i]}\n" +
                        "使用量:\t\t\t${use[i]}\n" +
                        "稀釋倍數:\t\t\t${multiple[i]}倍\n" +
                        "其他:\t\t\t${other[i]}")
                .setPositiveButton("刪除") { dialog, which ->
                    try {
                        AlertDialog.Builder(this)
                            .setTitle("刪除")                                                          //刪除
                            .setMessage("確定刪除紀錄內容?")
                            .setPositiveButton("確定") { dialog, which ->
                                try {
                                    dbrw.execSQL("DELETE FROM FarmWorkDB WHERE id_ LIKE '${id_[i]}'")   //挑點選的那行
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
                                    Toast.makeText(this, "cancle", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                                }
                            }.show()

                    } catch (e: Exception) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("編輯") { dialog, which ->                                 //編輯
                    try {
                        Log.d("dddddddd", "${id_[i]}")
                        val intent = Intent(this, worm_work_edit::class.java)
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
        val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB",null)
        c.moveToFirst()
        items.clear()
        for (i in 0 until c.count){
//            if (c.getString(8).length > 0){
            items.add("日期:${c.getString(1)}\n" +
                    "${c.getString(2)}${c.getString(3)}\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t${c.getString(11)}\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t${c.getString(12)}\n" +
                    "農作物:\t\t\t\t\t\t\t${c.getString(0)}\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t防治對象:${c.getString(10)}\n" +
                    "使用量:\t\t\t\t\t\t\t${c.getString(13)}\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t稀釋倍數:${c.getString(14)}倍\n" +
                    "其他防治方法:${c.getString(15)}")
//                }
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