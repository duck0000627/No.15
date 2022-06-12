package com.example.no15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)     //看資料庫的套件程式

        btn_boss.setOnClickListener {
//            loginalert_boss()   //老闆登入驗證
            startActivity(Intent(this, Boss_login::class.java)) //切換到老闆的登入介面
        }


        btn_employee.setOnClickListener {
//            loginalert_imployee()    //員工登入驗證
            startActivity(Intent(this, Employee_login::class.java)) //切換到員工的登入介面
        }
    }

}