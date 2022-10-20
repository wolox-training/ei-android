package com.example.woloxapp.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val background = getResources().getDrawable(R.drawable.gradient_util);
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        requireActivity().window.setStatusBarColor(getResources().getColor(android.R.color.transparent))
        requireActivity().window.setBackgroundDrawable(background);

        with(binding) {
            val viewPager2 = viewPager2
            val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
            viewPager2.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager2) {
                    tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "News"
                        tab.icon = activity?.let { ResourcesCompat.getDrawable(it.resources, R.drawable.ic_news_list_off, null) }
                    }
                    1 -> {
                        tab.text = "Profile"
                        tab.icon = activity?.let { ResourcesCompat.getDrawable(it.resources, R.drawable.ic_profile_on, null) }
                    }
                }
            }.attach()
            for (i in 0..tabLayout.tabCount) {
                val params = tabLayout.getTabAt(i)?.view?.getChildAt(0)?.layoutParams as LinearLayout.LayoutParams?
                params?.bottomMargin = -10
                tabLayout.getTabAt(i)?.view?.getChildAt(0)?.layoutParams = params
            }
        }
    }
}
