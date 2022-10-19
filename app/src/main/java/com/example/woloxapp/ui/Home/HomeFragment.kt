package com.example.woloxapp.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentHomescreenBinding
import com.example.woloxapp.ui.Home.tablayout.adapters.ViewPagerAdapter
import com.example.woloxapp.ui.login.LoginViewModel
import com.google.android.material.tabs.TabLayout
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
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        with(binding) {
            val viewPager2 = viewPager2
            val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
            viewPager2.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager2) {
                    tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "News"
                    }
                    1 -> {
                        tab.text = "Profile"
                    }
                }
            }.attach()
        }
    }
}
