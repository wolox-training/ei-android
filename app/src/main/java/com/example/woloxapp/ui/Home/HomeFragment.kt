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
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentHomescreenBinding
import com.example.woloxapp.ui.Home.tablayout.adapters.PagesConstants
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
        setStatusBar()
        with(binding) {
            loginViewModel.userIsLogged.observe(viewLifecycleOwner) {
                if (!it) findNavController().navigate(R.id.loginFragment)
            }
            val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
            viewPager2.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when (position) {
                    PagesConstants.NEWS_FRAGMENT_INDEX -> {
                        tab.text = getString(R.string.tab_news)
                        tab.icon = activity?.let {
                            ResourcesCompat.getDrawable(
                                it.resources,
                                R.drawable.ic_news_list_off,
                                null
                            )
                        }
                    }
                    PagesConstants.PROFILE_FRAGMENT_INDEX -> {
                        tab.text = getString(R.string.tab_profile)
                        tab.icon = activity?.let {
                            ResourcesCompat.getDrawable(
                                it.resources,
                                R.drawable.ic_profile_on,
                                null
                            )
                        }
                    }
                }
            }.attach()
            setPaddingTabLayout(tabLayout)
        }
    }

    private fun setStatusBar() {
        val background = getResources().getDrawable(R.drawable.gradient_status_bar)
        val activityWindow = requireActivity().window
        activityWindow.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        activityWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activityWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activityWindow.setStatusBarColor(getResources().getColor(android.R.color.transparent))
        activityWindow.setBackgroundDrawable(background)
    }

    private fun setPaddingTabLayout(tabLayout: TabLayout) {
        for (i in 0..tabLayout.tabCount) {
            val params =
                tabLayout.getTabAt(i)?.view?.getChildAt(0)?.layoutParams as LinearLayout.LayoutParams?
            params?.bottomMargin = marginBottom
            tabLayout.getTabAt(i)?.view?.getChildAt(0)?.layoutParams = params
        }
    }

    companion object {
        private const val marginBottom: Int = -10
    }
}
