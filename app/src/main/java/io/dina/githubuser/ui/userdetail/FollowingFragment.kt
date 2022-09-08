package io.dina.githubuser.ui.userdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.dina.githubuser.R
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.databinding.FragmentFollowingBinding
import io.dina.githubuser.ui.adapter.ListUserAdapter
import io.dina.githubuser.utility.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private val binding by viewBinding(FragmentFollowingBinding::bind)
    private val viewModel: FollowingViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getResult().observe(viewLifecycleOwner) { result ->

            binding.shimmerList.root.isVisible = result is ResultWrapper.Loading
            binding.rvUsers.isVisible = result is ResultWrapper.Success
            when (result) {
                is ResultWrapper.Error -> {
                    binding.shimmerList.root.stopShimmer()
                    Toast.makeText(
                        requireContext(),
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultWrapper.Success -> {
                    binding.shimmerList.root.stopShimmer()
                    binding.apply {
                        rvUsers.layoutManager = LinearLayoutManager(requireContext())
                        val adapter = ListUserAdapter(result.data.sortedBy { it.login })
                        adapter.callback = {
                            val userDetailIntent =
                                Intent(requireContext(), UserDetailActivity::class.java)
                            userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER_NAME, it.login)
                            startActivity(userDetailIntent)
                        }
                        rvUsers.adapter = adapter
                    }
                }
                ResultWrapper.Loading -> {
                    binding.shimmerList.root.startShimmer()
                }
            }
        }

        val userName = requireArguments().getString(USER_NAME).orEmpty()
        viewModel.getUserFollowing(userName)
    }

    companion object {
        const val USER_NAME = "userName"
        fun newInstance(userName: String): FollowingFragment {
            return FollowingFragment().apply {
                arguments = bundleOf(USER_NAME to userName)
            }
        }
    }
}