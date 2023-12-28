package com.raka.suitmediatest.ui.firstscreen

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.raka.suitmediatest.R
import com.raka.suitmediatest.databinding.ActivityFirstBinding
import com.raka.suitmediatest.ui.secondscreen.SecondActivity

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(R.color.blue, theme)
            }
        }

        binding.btnCheck.setOnClickListener {
            val palindrome = binding.etPalindrome.text.toString()
            val isPalindrome = if(checkSentence(palindrome)) "isPalindrom" else "not palindrome"
            if (palindrome.isEmpty()) {
                showDialogAlert(this, "Please insert the text")
            }else{
                showDialogAlert(this, isPalindrome)
            }

        }

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.NAME_TAG, name)
            if (name.isEmpty()) {
                showDialogAlert(this, "Please insert your name")
            }else{
                startActivity(intent)
            }
        }
    }

    private fun showDialogAlert(context: Context, isPalindrome: String) {
        AlertDialog.Builder(context).apply {
            setTitle("Palindrome Check")
            setMessage(isPalindrome)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun checkSentence(sentence: String): Boolean{
        val cleanSentence = sentence.lowercase().replace("\\s".toRegex(), "")
        return cleanSentence == cleanSentence.reversed()
    }
}