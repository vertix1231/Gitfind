package com.dicoding.bangkit.android.fundamental.gitfind.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.alarm.AlarmReceiver

class NotificationSettingsActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "SettingPref"
        private const val DAILY = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var swDaily : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.setting)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setSwitch()
        swDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                        this,
                        AlarmReceiver.TYPE_DAILY,
                        getString(R.string.daily_message)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun setSwitch() {
        swDaily.isChecked = mSharedPreferences.getBoolean(DAILY, false)
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}