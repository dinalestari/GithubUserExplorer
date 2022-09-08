package io.dina.githubuser.ui.searchuser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.dina.githubuser.R
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.data.preferences.SettingPreferences
import io.dina.githubuser.databinding.ActivitySearchUserBinding
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.ui.adapter.ListUserAdapter
import io.dina.githubuser.ui.favoriteuser.FavoriteUserActivity
import io.dina.githubuser.ui.settings.SettingsActivity
import io.dina.githubuser.ui.settings.SettingsViewModel
import io.dina.githubuser.ui.settings.SettingsViewModelFactory
import io.dina.githubuser.ui.userdetail.UserDetailActivity
import io.dina.githubuser.utility.showToast
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchUserBinding
    private val viewModel: SearchViewModel by viewModel()
    private val queryFlow = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
        setupSearchListener()
        viewModel.getUserList()

        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingsViewModelFactory(settingPreferences)).get(
                SettingsViewModel::class.java
            )
        binding.apply {
            settingViewModel.getThemeSettings()
                .observe(this@SearchUserActivity) { isDarkModeActive ->
                    if (isDarkModeActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list_favorite -> startActivity(
                Intent(
                    this,
                    FavoriteUserActivity::class.java
                )
            )
            R.id.action_theme_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupListener() {
        lifecycleScope.launch {

            queryFlow.debounce(300)
                .map {
                    if (it.isBlank()) viewModel.getUserList()
                    it
                }
                .filter { it.isNotBlank() }
                .distinctUntilChanged().collect {
                    viewModel.searchUser(it)
                }
        }
        viewModel.getResult().observe(this) {
            binding.shimmerList.root.isVisible = it is ResultWrapper.Loading
            binding.rvUsers.isVisible = it is ResultWrapper.Success
            when (it) {
                is ResultWrapper.Error -> {
                    binding.shimmerList.root.stopShimmer()
                    showToast(it.message)
                }
                is ResultWrapper.Success -> {
                    binding.shimmerList.root.stopShimmer()
                    setupList(it.data)
                }
                ResultWrapper.Loading -> {
                    binding.shimmerList.root.startShimmer()
                }
            }
        }
    }

    private fun setupSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    queryFlow.emit(newText.orEmpty())
                }
                return true
            }

        })
    }

    private fun setupList(users: List<User>) {
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@SearchUserActivity)
            val adapter = ListUserAdapter(users.sortedBy { it.login })
            adapter.callback = {
                val userDetailIntent =
                    Intent(this@SearchUserActivity, UserDetailActivity::class.java)
                userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER_NAME, it.login)
                startActivity(userDetailIntent)
            }
            rvUsers.adapter = adapter
        }
    }


}