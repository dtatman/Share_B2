package com.example.share_b2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Khai báo các phần tử giao diện
    private lateinit var switchDarkMode: Switch
    private lateinit var fontSizeSpinner: Spinner

    // Khóa cho SharedPreferences
    private val PREFS_NAME = "user_settings"
    private val KEY_DARK_MODE = "dark_mode"
    private val KEY_FONT_SIZE = "font_size"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các thành phần giao diện
        switchDarkMode = findViewById(R.id.switchDarkMode)
        fontSizeSpinner = findViewById(R.id.fontSizeSpinner)

        // Thiết lập SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Thiết lập Spinner với các giá trị từ resources
        val fontSizeOptions = resources.getStringArray(R.array.font_size_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fontSizeOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fontSizeSpinner.adapter = adapter

        // Khôi phục các cài đặt từ SharedPreferences
        loadSettings()

        // Xử lý sự kiện cho Switch chế độ tối
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeSetting(isChecked)
        }

        // Xử lý sự kiện khi người dùng chọn kích thước chữ từ Spinner
        fontSizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveFontSizeSetting(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không cần xử lý gì ở đây
            }
        }
    }

    // Hàm lưu chế độ tối vào SharedPreferences
    private fun saveDarkModeSetting(isDarkMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_DARK_MODE, isDarkMode)
        editor.apply()
    }

    // Hàm lưu kích thước chữ vào SharedPreferences
    private fun saveFontSizeSetting(fontSize: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_FONT_SIZE, fontSize)
        editor.apply()
    }

    // Hàm khôi phục các cài đặt từ SharedPreferences
    private fun loadSettings() {
        val isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        val fontSize = sharedPreferences.getInt(KEY_FONT_SIZE, 1) // Mặc định là "vừa"

        switchDarkMode.isChecked = isDarkMode
        fontSizeSpinner.setSelection(fontSize)
    }
}
