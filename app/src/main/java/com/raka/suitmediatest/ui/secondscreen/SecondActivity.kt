package com.raka.suitmediatest.ui.secondscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.suitmediatest.R
import com.raka.suitmediatest.data.local.DataFlashSale
import com.raka.suitmediatest.databinding.ActivitySeconBinding
import com.raka.suitmediatest.ui.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeconBinding
    private lateinit var flashSaleAdapter: FlashSaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(R.color.white, theme)
            }
        }

        setUpFlAdapter()

        val name = intent.getStringExtra(NAME_TAG)
        binding.tvName.text = name
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ACTIVITY2)
        }

        object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                // Ketika Aplikasi pertama kali membuka applikasi code ini akan dijalankan sealama 10 detik
                binding.tvCountdown.text = "Flashsale Countdown: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                // setelah 10 detik dijalankan, maka akan masuk kedalam fungsi ini
                binding.tvCountdown.text = "Flashsale Countdown: 0:0:0"
                Handler().postDelayed({
                    binding.tvCountdown.visibility = View.GONE
                    binding.rvFlashsale.visibility = View.GONE
                    binding.tvFlashSaleDone.visibility = View.VISIBLE
                }, 500)
            }
            // NOTED: Saya belum menambahkan Start() ketika di compile
        }.start()
    }

    private fun setUpFlAdapter() {
        val dataFlashsale = resources.getStringArray(R.array.data)
        val listData = ArrayList<DataFlashSale>()
        for (i in listData.indices){
            val dataFlashSale = DataFlashSale(
                dataFlashsale[i],
            )
            listData.add(dataFlashSale)
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFlashsale.layoutManager = layoutManager
        flashSaleAdapter = FlashSaleAdapter(listData)
        binding.rvFlashsale.adapter = flashSaleAdapter
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