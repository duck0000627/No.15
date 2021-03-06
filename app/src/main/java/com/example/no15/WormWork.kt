package com.example.no15

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
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
    private var Crop:String? = ""
    private var Date:String? = ""
    private var Code:String? = ""
    private var Number:String? = ""
    private var Work:String? = ""
    private var Tips:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worm_work)

        intent?.extras?.let {
            val a = it.getString("mucktype")
            val b = it.getString("muckname")
            val c = it.getString("count")
            val d = it.getString("counttype")
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
            Crop = crop
            Number = number
            Date = date
            Code = code
            Work = work
            Tips = tips
        }


        toolbar_Wormwork.setNavigationOnClickListener{
            Log.d("Alert","1")
            AlertDialog.Builder(this)    //?????????????????????
                .setTitle("??????")
                .setMessage("?????????????????????????")
                .setNegativeButton("??????"){
                        dialog, which->
                    Toast.makeText(this,"??????", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("??????") {dialog,which ->
                    startActivity(Intent(this, FarmWork::class.java)) //?????????????????????
                }.show()
        }

        val wormnamearray = arrayListOf("?????????-??????????????????","?????????-????????????")       //????????????
        val arrayAdapter_wormname = ArrayAdapter(this,android.R.layout.simple_spinner_item,wormnamearray)
        spinner_wormname.adapter = arrayAdapter_wormname

        dbrw = MyDBHelper(this).writableDatabase     //???????????????
        btn_worm_add.setOnClickListener {
            if (editText_WormWho.length()<1 || editText_WormNumber.length()<1 || editText_WormUse.length()<1 || editText_WormMultiple.length()<1){
                Toast.makeText(this,"????????????",Toast.LENGTH_SHORT).show()
            }else {
                try {
                    who = editText_WormWho.text.toString()
                    name = spinner_wormname.selectedItem.toString()
                    number = editText_WormNumber.text.toString()
                    use = editText_WormUse.text.toString()
                    multiple = editText_WormMultiple.text.toString()
                    other = editText_WormOther.text.toString()
                    val bundle = Bundle()
                    bundle.putString("who","${who}")
                    bundle.putString("name","${name}")
                    bundle.putString("wormnumber","${number}")
                    bundle.putString("use","${use}")
                    bundle.putString("multiple","${multiple}")
                    bundle.putString("other","${other}")
                    bundle.putString("mucktype","${A}")
                    bundle.putString("muckname","${B}")
                    bundle.putString("count","${C}")
                    bundle.putString("counttype","${D}")
                    bundle.putString("crop","${Crop}")
                    bundle.putString("code","${Code}")
                    bundle.putString("number","${Number}")
                    bundle.putString("tips","${Tips}")
                    bundle.putString("work","${Work}")
                    bundle.putString("date","${Date}")
                    val intent = Intent(this,FarmWork::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "${number}", Toast.LENGTH_SHORT).show()
                    editText_WormWho.setText("")
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
        dbrw.close()  //???????????????
        super.onDestroy()
    }

    override fun onBackPressed() {                  //????????????
        AlertDialog.Builder(this)    //?????????????????????
            .setTitle("??????")
            .setMessage("?????????????????????????")
            .setNegativeButton("??????"){
                    dialog, which->
                Toast.makeText(this,"??????", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("??????") {dialog,which ->
                val bundle = Bundle()                           //?????????
                bundle.putString("crop","${Crop}")
                bundle.putString("code","${Code}")
                bundle.putString("number","${Number}")
                bundle.putString("tips","${Tips}")
                bundle.putString("work","${Work}")
                bundle.putString("date","${Date}")
                val intent = Intent(this,FarmWork::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }.show()
    }
}