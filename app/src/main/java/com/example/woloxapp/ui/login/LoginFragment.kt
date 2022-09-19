package com.example.woloxapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.woloxapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var _binding: LoginFragmentBinding
    private val binding get() = _binding
    private fun uppercaseString(_button: Button): String {
        val uppercaseStringButton = _button.text.toString().uppercase()
        val tempButton = uppercaseStringButton.also { _button.text = it }
        return tempButton
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val loginBtn = binding.loginBtn
        val signupBtn = binding.signupBtn
        uppercaseString(loginBtn)
        uppercaseString(signupBtn)
        return binding.root
    }
}
