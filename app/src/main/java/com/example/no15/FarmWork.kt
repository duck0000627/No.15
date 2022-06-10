package com.example.no15

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*

class FarmWork : AppCompatActivity() {

    private lateinit var dbrw: SQLiteDatabase
    private var A:String? = ""
    private var B:String? = ""
    private var C:String? = ""
    private var D:String? = ""
    private var E:String? = ""
    private var F:String? = ""
    private var G:String? = ""
    private var H:String? = ""
    private var I:String? = ""
    private var J:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_work)

        intent?.extras?.let {
            val a = it.getString("mucktype")
            val b = it.getString("muckname")
            val c = it.getString("count")
            val d = it.getString("counttype")
            val e = it.getString("who")
            val f = it.getString("name")
            val g = it.getString("use")
            val h = it.getString("multiple")
            val i = it.getString("other")
            val j = it.getString("number")
            A = a
            B = b
            C = c
            D = d
            E = e
            F = f
            G = g
            H = h
            I = i
            J = j
        }
        val mucktypeselect = A
        val muckname = B
        val count = C
        val counttype = D
        val who = E
        val name = F
        val use = G
        val multiple = H
        val other = I
        val number = J


        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

//        button_Farmwork_add.setOnClickListener {     //debug用
//            Toast.makeText(this,"${mucktypeselect}${number}",Toast.LENGTH_SHORT).show()
//        }

        val code = arrayListOf("A","B","C","D","E","F")       //田區代號
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,code)
        spinner_Code.adapter = arrayAdapter
        spinner_Code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{         //下拉框被選之後
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (code[p2] == "A"){
                    A()
                }
                if (code[p2] == "F"){
                    F()
                }
                if (code[p2] == "B" || code[p2] == "C" || code[p2] == "D" || code[p2] == "E"){
                    BCDE()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        button_to_muck.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("who","${who}")
            bundle.putString("name","${name}")
            bundle.putString("number","${number}")
            bundle.putString("use","${use}")
            bundle.putString("multiple","${multiple}")
            bundle.putString("other","${other}")
            val intent = Intent(this,MuckWork::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
//            startActivity(Intent(this,MuckWork::class.java))       //肥料使用紀錄按鈕
        }
        button_to_worm.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("mucktype","${mucktypeselect}")
            bundle.putString("muckname","${muckname}")
            bundle.putString("count","${count}")
            bundle.putString("counttype","${counttype}")
            val intent = Intent(this,WormWork::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
//            startActivity(Intent(this,WormWork::class.java))       //防治使用紀錄按鈕
        }

        button_Farmwork_add.setOnClickListener {                      //新增資料
            if (editText_Crop.length()<1 || editText_Date.length()<1 || editText_Work.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbrw.execSQL(
                        "INSERT INTO FarmWorkDB(crop,date,code,number,work,tips," +
                                "mucktype,muckname,muckcount,muckcounttype," +
                                "wormwho,wormname,wormnumber,wormuse,wormmultiple,wormother) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        arrayOf(
                            editText_Crop.text.toString(),  //0  農作物
                            editText_Date.text.toString(),  //1  日期
                            spinner_Code.selectedItem.toString(),  //2  田區
                            spinner_Number.selectedItem.toString(),  //3 號碼
                            editText_Work.text.toString(),  //4  工作
                            editText_Tips.text.toString(),  //5  備註
                            mucktypeselect.toString(),  //6  mucktype
                            muckname.toString(),  //7  muckname
                            count.toString(),  //8  muckcount
                            counttype.toString(),  //9  muckcounttype
                            who.toString(),  //10  wormwho
                            name.toString(),  //11  wormname
                            number.toString(),//12  wormnumber
                            use.toString(),  //13 wormuse
                            multiple.toString(),  //14  wormmultiple
                            other.toString()      //15  wormother
                        )
                    )
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, FarmworkData::class.java))
                editText_Crop.setText("")
                editText_Date.setText("")
                editText_Work.setText("")
                editText_Tips.setText("")
            }
        }

    }


    override fun onDestroy() {
        dbrw.close()  //關閉資料庫
        super.onDestroy()
    }



    override fun onBackPressed() {    //上一頁
        AlertDialog.Builder(this)    //會跳一個提示框
            .setTitle("捨棄")
            .setMessage("確定捨棄紀錄內容?")
            .setNegativeButton("取消"){
                    dialog, which->
                Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("捨棄") {dialog,which ->
                startActivity(Intent(this, FarmworkData::class.java)) //案箭頭回上一頁
            }.show()
    }



    private fun addListener() {         //新增資料

    }



    private fun A(){                             //選到A時的函式
        val number_A = arrayListOf("1","2","1~2")
        val arrayAdapter_A = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_A)
        spinner_Number.adapter = arrayAdapter_A
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun F(){                                //選到F時的函式
        val number_F = arrayListOf("1","2","3","1~3")
        val arrayAdapter_F = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_F)
        spinner_Number.adapter = arrayAdapter_F
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@FarmWork,number_F[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun BCDE(){                                         //選到BCDE時的函式
        val number_BCDE = arrayListOf("1","2","3","4","5","6","1~6")
        val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
        spinner_Number.adapter = arrayAdapter_BCDE
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@FarmWork,number_BCDE[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

//    private val resultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (RESULT_OK == activityResult.resultCode) {
//                Log.d(
//                    "maho",
//                    "回傳: ${activityResult.data?.getStringExtra(it.mucktype)}"
//                )
//            }

}