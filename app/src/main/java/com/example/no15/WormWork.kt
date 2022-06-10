package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*
import kotlinx.android.synthetic.main.activity_muck_work.*
import kotlinx.android.synthetic.main.activity_worm_work.*

class WormWork : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase

    private var who:String = ""
    private var name:String = ""
    private var number:String = ""
    private var use:String = ""
    private var multiple:String = ""
    private var other:String = ""
    private var A:String? = ""
    private var B:String? = ""
    private var C:String? = ""
    private var D:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worm_work)

        intent?.extras?.let {
            val a = it.getString("mucktype")
            val b = it.getString("muckname")
            val c = it.getString("count")
            val d = it.getString("counttype")
            A = a
            B = b
            C = c
            D = d
        }


        toolbar_Wormwork.setNavigationOnClickListener{
            Log.d("Alert","1")
            AlertDialog.Builder(this)    //會跳一個提示框
                .setTitle("捨棄")
                .setMessage("確定捨棄紀錄內容?")
                .setNegativeButton("取消"){
                        dialog, which->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("捨棄") {dialog,which ->
                    startActivity(Intent(this, FarmWork::class.java)) //案箭頭回上一頁
                }.show()
        }

        toolbar_Wormwork.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.toolbar_success -> {         //當打勾按下去
                    startActivity(Intent(this,FarmWork::class.java))       //回到上一頁
                }
            }
            false
        }

        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫
        btn_worm_add.setOnClickListener {
            if (editText_WormWho.length()<1 || editText_WormName.length()<1 || editText_WormNumber.length()<1 || editText_WormUse.length()<1 || editText_WormMultiple.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
//                    dbrw.execSQL(
//                        "INSERT INTO WormWorkDB(who,name,number,use,multiple,other) VALUES(?,?,?,?,?,?)",
//                        arrayOf(
//                            editText_WormWho.text.toString(),
//                            editText_WormName.text.toString(),
//                            editText_WormNumber.text.toString(),
//                            editText_WormUse.text.toString(),
//                            editText_WormMultiple.text.toString(),
//                            editText_WormOther.text.toString()
//                        )
//                    )
                    who = editText_WormWho.text.toString()
                    name = editText_WormName.text.toString()
                    number = editText_WormNumber.text.toString()
                    use = editText_WormUse.text.toString()
                    multiple = editText_WormMultiple.text.toString()
                    other = editText_WormOther.text.toString()
                    val bundle = Bundle()
                    bundle.putString("who","${who}")
                    bundle.putString("name","${name}")
                    bundle.putString("number","${number}")
                    bundle.putString("use","${use}")
                    bundle.putString("multiple","${multiple}")
                    bundle.putString("other","${other}")
                    bundle.putString("mucktype","${A}")
                    bundle.putString("muckname","${B}")
                    bundle.putString("count","${C}")
                    bundle.putString("counttype","${D}")
                    val intent = Intent(this,FarmWork::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    editText_WormWho.setText("")
                    editText_WormName.setText("")
                    editText_WormNumber.setText("")
                    editText_WormUse.setText("")
                    editText_WormMultiple.setText("")
                    editText_WormOther.setText("")
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)    //會跳一個提示框
            .setTitle("捨棄")
            .setMessage("確定捨棄紀錄內容?")
            .setNegativeButton("取消"){
                    dialog, which->
                Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("捨棄") {dialog,which ->
                startActivity(Intent(this, FarmWork::class.java)) //案箭頭回上一頁
            }.show()
    }
}