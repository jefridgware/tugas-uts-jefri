package com.example.myappgit.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myappgit.R
import com.google.android.material.switchmaterial.SwitchMaterial
class ModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        // Membaca preferensi mode terakhir dari SharedPreferences
        val sharedPref = getSharedPreferences("myAppPrefs", MODE_PRIVATE)
        val isNightMode = sharedPref.getBoolean("nightMode", false)

        // Menetapkan status SwitchMaterial sesuai dengan preferensi mode terakhir
        switchTheme.isChecked = isNightMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Menyimpan preferensi mode terang atau gelap ke SharedPreferences
            val editor = sharedPref.edit()
            editor.putBoolean("nightMode", isChecked)
            editor.apply()

            // Mengatur mode aplikasi sesuai dengan preferensi yang dipilih
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
