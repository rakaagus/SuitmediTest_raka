package com.raka.suitmediatest.ui.secondscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raka.suitmediatest.R
import com.raka.suitmediatest.databinding.ActivitySeconBinding
import com.raka.suitmediatest.ui.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(R.color.white, theme)
            }
        }

        val name = intent.getStringExtra(NAME_TAG)
        binding.tvName.text = name
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ACTIVITY2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ACTIVITY2 && resultCode == RESULT_OK) {
            // Ambil data yang dikirimkan dari aktivitas kedua
            val receivedData = data?.getStringExtra("resultData")
            // Lakukan sesuatu dengan data yang diterima
            binding.tvUsername.text = receivedData
        }
    }

    companion object{
        const val NAME_TAG = "NameTag"
        const val REQUEST_CODE_ACTIVITY2 = 123
    }
}