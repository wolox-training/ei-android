package com.example.woloxapp.ui.AuthManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentAuthManagerBinding
import com.example.woloxapp.ui.login.LoginViewModel

class AuthManager : Fragment() {
    lateinit var binding: FragmentAuthManagerBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthManagerBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.getUserModel()
        userLogger()
    }
    private fun userLogger() {
        loginViewModel.userIsLogged.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(R.id.HomePageFragment)
            else findNavController().navigate(R.id.loginFragment)
        }
    }
}
