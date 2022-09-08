package io.dina.githubuser.ui.userdetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import io.dina.githubuser.R
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.databinding.ActivityUserDetailBinding
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.utility.showBackButton
import io.dina.githubuser.utility.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModel()
    private val tabTitles = listOf(R.string.tab_following, R.string.tab_followers)
    private val userName by lazy {
        intent.getStringExtra(EXTRA_USER_NAME).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBackButton()
        supportActionBar?.title = getString(R.string.detail_page_title)


        val sectionPagerAdapter = SectionPagerAdapter(this, userName)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = getString(tabTitles[position])
            }.attach()
        }

        viewModel.getResult().observe(this) {
            binding.shimmerDetail.root.isVisible = it is ResultWrapper.Loading
            when (it) {
                is ResultWrapper.Error -> {
                    binding.shimmerDetail.root.stopShimmer()
                    showToast(it.message)
                }
                is ResultWrapper.Success -> {
                    binding.shimmerDetail.root.stopShimmer()
                    setUserData(it.data)
                }
                ResultWrapper.Loading -> {
                    binding.shimmerDetail.root.startShimmer()
                }
            }
        }

        viewModel.getUser(userName)
    }

    private fun setUserData(user: User) {
        binding.apply {
            tvName.text = user.name
            tvUsername.text = user.login
            tvFollower.text = getString(R.string.follower_text, user.followers).getSpannedText()
            tvFollowing.text = getString(R.string.following_text, user.following).getSpannedText()
            tvRepository.text = getString(R.string.repository_text, user.publicRepos)
            tvLocation.text = user.location.ifEmpty { "-" }
            tvCompany.text = user.company
            imgAvatar.load(user.avatarUrl)

            lifecycleScope.launchWhenCreated {
                val isFavorited = viewModel.isFavorited(user.login)
                if (isFavorited) {
                    binding.btnFavorite.setColorFilter(
                        ContextCompat.getColor(
                            this@UserDetailActivity,
                            R.color.red
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                btnFavorite.setOnClickListener {
                    toggleFavoriteUser(user)
                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun String.getSpannedText(): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(this)
        }
    }

    private fun toggleFavoriteUser(user: User) {
        lifecycleScope.launchWhenCreated {
            val isFavorited = viewModel.isFavorited(user.login)
            val color: Int
            if (isFavorited) {
                color = R.color.white
                viewModel.deleteFavoriteUser(user)
                showToast("This user isn't your favorite anymore!")
            } else {
                color = R.color.red
                viewModel.insertFavoriteUser(user)
                showToast("This user is your favorite!")
            }

            binding.btnFavorite.setColorFilter(
                ContextCompat.getColor(
                    this@UserDetailActivity,
                    color
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    companion object {
        const val EXTRA_USER_NAME = "extra_user"
    }

}