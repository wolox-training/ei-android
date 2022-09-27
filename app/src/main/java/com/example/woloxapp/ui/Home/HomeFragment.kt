package com.example.woloxapp.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentHomescreenBinding
import com.example.woloxapp.ui.login.LoginViewModel

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
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)

        val buttonPrimary = binding.buttonPrimary
        val logoutBtn = binding.logoutBtn
        buttonPrimary.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        logoutBtn.setOnClickListener {
            if (::loginViewModel.isInitialized) {
                loginViewModel.logout()
            }
        }
    }
}
