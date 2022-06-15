package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_farm_work.*
import kotlinx.android.synthetic.main.activity_farm_work_edit.*

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
    private var Crop:String? = ""
    private var Date:String? = ""
    private var Code:String? = ""
    private var Number:String? = ""
    private var Work:String? = ""
    private var Tips:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_work)

        intent?.extras?.let {                       //接收資料
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
            val crop = it.getString("crop")
            val date = it.getString("date")
            val code = it.getString("code")
            val number = it.getString("number")
            val work = it.getString("work")
            val tips = it.getString("tips")
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
            Crop = crop
            Number = number
            Date = date
            Code = code
            Work = work
            Tips = tips
        }
        var mucktypeselect = A
        var muckname = B
        var count = C
        var counttype = D
        var who = E
        var name = F
        var use = G
        var multiple = H
        var other = I
        var wormnumber = J

//        editText_Crop.setText("${Crop}")
        editText_Date.setText("${Date}")
        editText_Work.setText("${Work}")
        editText_Tips.setText("${Tips}")

//        Toast.makeText(this,"${Number}",Toast.LENGTH_SHORT).show()


        dbrw = MyDBHelper(this).writableDatabase     //取得資料庫

        val code = arrayListOf("A","B","C","D","E","F")       //田區代號
        val arrayAdapter_code = ArrayAdapter(this,android.R.layout.simple_spinner_item,code)
        spinner_Code.adapter = arrayAdapter_code
        seleceCode()
        spinner_Code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{         //下拉框被選之後
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0){
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

        val crop = arrayListOf("黑豆","黃豆")       //田區代號
        val arrayAdapter_crop = ArrayAdapter(this,android.R.layout.simple_spinner_item,crop)
        spinner_Crop.adapter = arrayAdapter_crop
        if ("${Crop}" == "黑豆")
        {
            spinner_Crop.setSelection(0,true)
        }
        if ("${Crop}" == "黃豆")
        {
            spinner_Crop.setSelection(1,true)
        }
        spinner_Crop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        button_to_muck.setOnClickListener{                 //肥料使用紀錄按鈕
            val bundle = Bundle()
            bundle.putString("who","${who}")
            bundle.putString("name","${name}")
            bundle.putString("number","${wormnumber}")
            bundle.putString("use","${use}")
            bundle.putString("multiple","${multiple}")
            bundle.putString("other","${other}")
            bundle.putString("crop","${spinner_Crop.selectedItem}")
            bundle.putString("date","${editText_Date.text}")
            bundle.putString("code","${spinner_Code.selectedItem}")
            bundle.putString("number","${spinner_Number.selectedItem}")
            bundle.putString("work","${editText_Work.text}")
            bundle.putString("tips","${editText_Tips.text}")
            val intent = Intent(this,MuckWork::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        button_to_worm.setOnClickListener{                    //防治使用紀錄按鈕
            val bundle = Bundle()
            bundle.putString("mucktype","${mucktypeselect}")
            bundle.putString("muckname","${muckname}")
            bundle.putString("count","${count}")
            bundle.putString("counttype","${counttype}")
            bundle.putString("crop","${spinner_Crop.selectedItem}")
            bundle.putString("date","${editText_Date.text}")
            bundle.putString("code","${spinner_Code.selectedItem}")
            bundle.putString("number","${spinner_Number.selectedItem}")
            bundle.putString("work","${editText_Work.text}")
            bundle.putString("tips","${editText_Tips.text}")
            val intent = Intent(this,WormWork::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        button_Farmwork_add.setOnClickListener {                                                             //新增資料
            if (editText_Date.length()<1 || editText_Work.length()<1){
                Toast.makeText(this,"請勿留空",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    if (mucktypeselect == "" || mucktypeselect == "null") {      //
                        mucktypeselect = "-"
                    }
                    if (muckname == "" || muckname == "null") {
                        muckname = "-"
                    }
                    if (count == "" || count == "null") {
                        count = "-"
                    }
                    if (counttype == "" || counttype == "null") {
                        counttype = "-"
                    }
                    if (who == "" || who == "null") {
                        who = "-"
                    }
                    if (name == "" || name == "null") {
                        name = "-"
                    }
                    if (wormnumber == "" || wormnumber == "null") {
                        wormnumber = "-"
                    }
                    if (use == "" || use == "null") {
                        use = "-"
                    }
                    if (multiple == "" || multiple == "null") {
                        multiple = "-"
                    }
                    if (other == "" || other == "null") {
                        other = "-"
                    }

                    dbrw.execSQL(
                        "INSERT INTO FarmWorkDB(crop,date,code,number,work,tips," +
                                "mucktype,muckname,muckcount,muckcounttype," +
                                "wormwho,wormname,wormnumber,wormuse,wormmultiple,wormother) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        arrayOf(
                            spinner_Crop.selectedItem.toString(),  //0  農作物
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
                            wormnumber.toString(),//12  wormnumber
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

    private fun seleceCode(){               //跳畫面選項會留者
        if ("${Code}" == "A")
        {
            spinner_Code.setSelection(0,true)
            A()
        }
        if ("${Code}" == "B")
        {
            spinner_Code.setSelection(1,true)
            BCDE()
        }
        else if ("${Code}" == "C")
        {
            spinner_Code.setSelection(2,true)
            BCDE()
        }
        else if ("${Code}" == "D")
        {
            spinner_Code.setSelection(3,true)
            BCDE()
        }
        else if ("${Code}" == "E")
        {
            spinner_Code.setSelection(4,true)
            BCDE()
        }
        else if("${Code}" == "F")
        {
            spinner_Code.setSelection(5,true)
            F()
        }
    }


    private fun A(){                             //選到A時的函式
        val number_A = arrayListOf("1","2","1~2")
        val arrayAdapter_A = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_A)
        spinner_Number.adapter = arrayAdapter_A
        if ("${Number}" == "1")
        {
            spinner_Number.setSelection(0,true)
        }
        if ("${Number}" == "2")
        {
            spinner_Number.setSelection(1,true)
        }
        if ("${Number}" == "1~2")
        {
            spinner_Number.setSelection(2,true)
        }
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
        if ("${Number}" == "1")
        {
            spinner_Number.setSelection(0,true)
        }
        if ("${Number}" == "2")
        {
            spinner_Number.setSelection(1,true)
        }
        if ("${Number}" == "3")
        {
            spinner_Number.setSelection(2,true)
        }
        if ("${Number}" == "1~3")
        {
            spinner_Number.setSelection(3,true)
        }
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@FarmWork,number_F[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun BCDE(){                                         //選到BCDE時的函式
        val number_BCDE = arrayListOf("1","2","3","4","5","6","1~3","3~6","1~6")
        val arrayAdapter_BCDE = ArrayAdapter(this,android.R.layout.simple_spinner_item,number_BCDE)
        spinner_Number.adapter = arrayAdapter_BCDE
        if ("${Number}" == "1")
        {
            spinner_Number.setSelection(0,true)
        }
        if ("${Number}" == "2")
        {
            spinner_Number.setSelection(1,true)
        }
        if ("${Number}" == "3")
        {
            spinner_Number.setSelection(2,true)
        }
        if ("${Number}" == "4")
        {
            spinner_Number.setSelection(3,true)
        }
        if ("${Number}" == "5")
        {
            spinner_Number.setSelection(4,true)
        }
        if ("${Number}" == "6")
        {
            spinner_Number.setSelection(5,true)
        }
        if ("${Number}" == "1~3")
        {
            spinner_Number.setSelection(6,true)
        }
        if ("${Number}" == "3~6")
        {
            spinner_Number.setSelection(7,true)
        }
        if ("${Number}" == "1~6")
        {
            spinner_Number.setSelection(8,true)
        }
        spinner_Number.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@FarmWork,number_BCDE[p2],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


}