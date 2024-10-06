package com.example.remindertask

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.remindertask.databinding.ActivityResultReminderBinding

class ResultReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra("EXTRA_TITLE")
        val date = intent.getStringExtra("EXTRA_DATE")
        val time = intent.getStringExtra("EXTRA_TIME")
        val repeat = intent.getStringExtra("EXTRA_REPEAT")
        with(binding){
            txtTitle.text = title
            txtDate.text = date
            txtTime.text = time
            txtRepeat.text = repeat
            btnBack.setOnClickListener{
                val intentToBack = Intent(this@ResultReminderActivity, TaskInputActivity::class.java)
                startActivity(intentToBack)
            }
        }

    }
}