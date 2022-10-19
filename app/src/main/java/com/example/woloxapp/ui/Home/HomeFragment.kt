package com.example.woloxapp.ui.Home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentHomescreenBinding
import com.example.woloxapp.ui.Home.tablayout.adapters.ViewPagerAdapter
import com.example.woloxapp.ui.login.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var _binding: FragmentHomescreenBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomescreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.getUserModel()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.GREEN
        with(binding) {
           /* logoutBtn.setOnClickListener {
                loginViewModel.logout()
                findNavController().navigate(R.id.loginFragment)
            }
            */
            loginViewModel.userIsLogged.observe(viewLifecycleOwner) {
                if (!it) findNavController().navigate(R.id.loginFragment)
            }
            val adapter = fragmentManager?.let { ViewPagerAdapter(it, lifecycle) }
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "News"
                    }
                    1 -> {
                        tab.text = "Profile"
                    }
                }
            }
        }
    }
}
