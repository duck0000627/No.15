package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_data_page.*


class DataPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_page)

        add_record.setOnClickListener {
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換到新增頁面
        }

        toolbar.setNavigationOnClickListener {
            if (layout_drawer.isDrawerOpen(navigation_drawer)) {            //如果側邊欄是開的就關掉
                layout_drawer.closeDrawer(navigation_drawer)
            } else {
                layout_drawer.openDrawer(navigation_drawer)                //如果側邊欄是關的就打開
            }
        }

        navigation_drawer.setNavigationItemSelectedListener {          //當側邊框按鈕被點選
            when (it.itemId) {
                R.id.drawer_farmwork -> {      //農場工作按鈕被點選
                    replaceFragement(FarmWork_Data_Fragement())  //切換fragement
                    layout_drawer.closeDrawer(navigation_drawer)   //收側邊框
                }
                R.id.drawer_muck -> {          //肥料按鈕被點選
                    replaceFragement(Muck_Data_Fragment())
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_worm -> {          //病蟲害按鈕被點選
                    replaceFragement(Worm_Data_Fragment())
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                R.id.drawer_other -> {         //其他按鈕被點選
                    replaceFragement(Other_Data_Fragment())
                    layout_drawer.closeDrawer(navigation_drawer)
                }
                else -> {}
            }
            return@setNavigationItemSelectedListener true
        }

    }

    private fun replaceFragement(fragment: Fragment) {     //切換fragement的函式
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragement, fragment)
        fragmentTransaction.commit()
    }
}