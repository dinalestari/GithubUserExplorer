package io.dina.githubuser.ui.favoriteuser

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.dina.githubuser.R
import io.dina.githubuser.databinding.ActivityFavoriteUserBinding
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.ui.adapter.ListUserAdapter
import io.dina.githubuser.ui.userdetail.UserDetailActivity
import io.dina.githubuser.utility.showBackButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel: FavoriteUserViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showBackButton()
        supportActionBar?.title = getString(R.string.favorite_user_title)

        viewModel.getAllFavoriteUser().observe(this) {
            setupList(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupList(users: List<User>) {
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            val adapter = ListUserAdapter(users.sortedBy { it.login })
            adapter.callback = {
                val userDetailIntent =
                    Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
                userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER_NAME, it.login)
                startActivity(userDetailIntent)
            }
            rvUsers.adapter = adapter
        }
    }
}