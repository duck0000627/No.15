package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_data_page.*


class DataPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_page)

        add_record.setOnClickListener{
            startActivity(Intent(this, FarmWork::class.java))                //按按鈕切換頁面
        }

        toolbar.setNavigationOnClickListener{
            if (layout_drawer.isDrawerOpen(navigation_drawer)){            //如果側邊欄是開的就關掉
                layout_drawer.closeDrawer(navigation_drawer)
            }else{
                layout_drawer.openDrawer(navigation_drawer)                //如果側邊欄是關的就打開
            }
        }



    }
}