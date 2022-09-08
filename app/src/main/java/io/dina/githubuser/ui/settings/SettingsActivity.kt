package io.dina.githubuser.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import io.dina.githubuser.R
import io.dina.githubuser.data.preferences.SettingPreferences
import io.dina.githubuser.databinding.ActivitySettingsBinding
import io.dina.githubuser.utility.showBackButton
import io.dina.githubuser.utility.showToast

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBackButton()
        supportActionBar?.title = getString(R.string.theme_settings_title)

        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, SettingsViewModelFactory(settingPreferences)).get(
            SettingsViewModel::class.java
        )
        binding.apply {
            viewModel.getThemeSettings().observe(this@SettingsActivity) { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    themeSwitch.isChecked = true
                    ivTheme.setImageResource(R.drawable.ic_baseline_nights_stay)
                    tvTheme.text = getString(R.string.dark_theme)
                    showToast("Dark mode is active")
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    themeSwitch.isChecked = false
                    ivTheme.setImageResource(R.drawable.ic_baseline_wb_sunny)
                    tvTheme.text = getString(R.string.light_theme)
                    showToast("Light mode is active")
                }
            }

            themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveThemeSetting(isChecked)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}