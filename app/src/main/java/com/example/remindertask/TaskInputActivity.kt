package com.example.remindertask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.remindertask.databinding.ActivityInputReminderBinding

class TaskInputActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var inputBinding: ActivityInputReminderBinding
    private val taskCalendar = Calendar.getInstance()

    private lateinit var taskDate: String
    private lateinit var taskTime: String
    private lateinit var repeatTask: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setup View Binding untuk tampilan
        inputBinding = ActivityInputReminderBinding.inflate(layoutInflater)
        setContentView(inputBinding.root)

        // Mengatur Spinner untuk pilihan repeat
        configureRepeatSpinner()

        // Setup DatePicker dan TimePicker
        setupDatePicker()
        setupTimePicker()

        // Ketika tombol ditambahkan diklik, tampilkan dialog konfirmasi
        inputBinding.btnToResult.setOnClickListener {
            showTaskConfirmationDialog()
        }
    }

    // Fungsi untuk mengatur spinner repeat
    private fun configureRepeatSpinner() {
        val repeatOptions = resources.getStringArray(R.array.repeat)
        val repeatAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, repeatOptions)
        inputBinding.spinnerRepeat.adapter = repeatAdapter

        inputBinding.spinnerRepeat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                repeatTask = repeatOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Kosongkan bila tidak ada pilihan
            }
        }
    }

    // Fungsi untuk menampilkan DatePicker
    private fun setupDatePicker() {
        inputBinding.selectDate.setOnClickListener {
            DatePickerDialog(
                this, this,
                taskCalendar.get(Calendar.YEAR),
                taskCalendar.get(Calendar.MONTH),
                taskCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    // Fungsi untuk menampilkan TimePicker
    private fun setupTimePicker() {
        inputBinding.selectTime.setOnClickListener {
            TimePickerDialog(
                this, this,
                taskCalendar.get(Calendar.HOUR_OF_DAY),
                taskCalendar.get(Calendar.MINUTE), false
            ).show()
        }
    }

    // Fungsi untuk menampilkan dialog konfirmasi
    private fun showTaskConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Task Confirmation")
            .setMessage("Do you want to add this as new task?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, ResultReminderActivity::class.java)
                intent.putExtra("EXTRA_TITLE", inputBinding.etTitle.text.toString())
                intent.putExtra("EXTRA_REPEAT", repeatTask)
                intent.putExtra("EXTRA_DATE", taskDate)
                intent.putExtra("EXTRA_TIME", taskTime)
                startActivity(intent)
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        alertDialog.create().show()
    }

    // Mengatur waktu yang dipilih pada TimePicker
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        taskTime = "$hourOfDay:$minute"
        inputBinding.selectTime.text = taskTime
    }

    // Mengatur tanggal yang dipilih pada DatePicker
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        taskDate = "$dayOfMonth/${month + 1}/$year"
        inputBinding.txtSelectDate.text = taskDate
    }
}
