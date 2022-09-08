package io.dina.githubuser.ui.userdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val userName: String) :
    FragmentStateAdapter(activity) {
    private var fragments =
        listOf<Fragment>(
            FollowingFragment.newInstance(userName),
            FollowersFragment.newInstance(userName)
        )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}