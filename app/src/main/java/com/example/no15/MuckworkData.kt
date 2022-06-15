package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var id_: ArrayList<String> = ArrayList()
    private var muckname:ArrayList<String> = ArrayList()
    private var type:ArrayList<String> = ArrayList()
    private var counttype:ArrayList<String> = ArrayList()
    private var count:ArrayList<String> = ArrayList()
    private var date:ArrayList<String> = ArrayList()
    private var code:ArrayList<String> = ArrayList()
    private var number:ArrayList<String> = ArrayList()
    private var crop:ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muckwork_data)

        LV_muckwork.bringToFront()

        dbrw = MyDBHelper(this).writableDatabase  //呼叫資料庫
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        LV_muckwork.adapter = adapter

        add_record_muckwork.setOnClickListener {
            //紀錄(筆)按鈕
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換到新增頁面
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
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    startActivity(Intent(this,WormworkData::class.java))
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    startActivity(Intent(this,OtherworkData::class.java))
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

        LV_muckwork.setOnItemClickListener { adapterView, view, i, l ->
            val c = dbrw.rawQuery("SELECT * FROM FarmWorkDB", null)
            c.moveToFirst()
            id_.clear()
            muckname.clear()
            type.clear()
            counttype.clear()
            count.clear()
            date.clear()
            code.clear()
            number.clear()
            crop.clear()
            for (i in 0 until c.count) {
                muckname.add("${c.getString(7)}")
                type.add("${c.getString(6)}")
                counttype.add("${c.getString(9)}")
                count.add("${c.getString(8)}")
                date.add("${c.getString(1)}")
                code.add("${c.getString(2)}")
                number.add("${c.getString(3)}")
                crop.add("${c.getString(0)}")
                id_.add("${c.getString(16)}")
                c.moveToNext()
            }

            AlertDialog.Builder(this)
                .setTitle("${date[i]}")
                .setMessage("田區:${code[i]}${number[i]}\n" +
                        "農作物:\t\t\t${crop[i]}\n" +
                        "施肥別:\t\t\t${type[i]}\n" +
                        "資材名稱 :\t${muckname[i]}\n" +
                        "使用量 :\t\t\t${count[i]}${counttype[i]}")
                .setPositiveButton("刪除") { dialog, which ->          //刪除
                    try {
                        AlertDialog.Builder(this)
                            .setTitle("刪除")                                                          //確定刪除
                            .setMessage("確定刪除紀錄內容?")
                            .setPositiveButton("確定") { dialog, which ->
                                try {
                                    dbrw.execSQL("DELETE FROM FarmWorkDB WHERE id_ LIKE '${id_[i]}'")
                                    Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show()
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
                .setNegativeButton("編輯") { dialog, which ->
                    try {
//                        val search = click
                        Log.d("dddddddd","${id_[i]}}")
                        val intent = Intent(this,muck_work_edit::class.java)
                        intent.putExtra("id","${id_[i]}")
                        startActivity(intent)
                        Toast.makeText(this, "編輯", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.d("ddddd","${id_[i]}}")
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
                items.add("${c.getString(1)}\n" +
                        "${c.getString(2)}${c.getString(3)}\t\t\t\t\t\t\t${c.getString(7)}\t\t\t\t\t\t${c.getString(6)}\n" +
                        "農作物:\t\t\t\t${c.getString(0)}\t\t\t\t\t\t使用量:${c.getString(8)}${c.getString(9)}")
//            }
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