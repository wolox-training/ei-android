package com.example.woloxapp.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var _binding: LoginFragmentBinding
    private val binding get() = _binding
    companion object {
        const val toastHorizontal = 0
        const val toastVertical = 20
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val urlWolox = getText(R.string.wolox_web).toString()
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.getUserModel()
        getObservers()
        validCredentials()
        with(binding) {
            loginBtn.setOnClickListener {
                val emailText = userName.text.toString()
                val passwordText = password.text.toString()
                loginViewModel.fieldsValidation(emailText, passwordText)
            }
            signupBtn.setOnClickListener {
                findNavController().navigate(R.id.go_to_signup)
            }
            woloxPhone.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlWolox))) }
        }
    }

    private fun getObservers() {
        val toast = Toast.makeText(context, getString(R.string.required_fields), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, toastHorizontal, toastVertical)
        loginViewModel.emptyFieldsError.observe(viewLifecycleOwner) {
            it?.let { toast.show() }
        }
        loginViewModel.validEmail.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.userName.error = getString(R.string.invalid_username)
            }
        }
        loginViewModel.userEmail.observe(viewLifecycleOwner) {
            it.let {
                binding.userName.setText(it)
            }
        }
        loginViewModel.userPassword.observe(viewLifecycleOwner) {
            it.let {
                binding.password.setText(it)
            }
        }
    }

    private fun validCredentials() {
        loginViewModel.validEmail.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(R.id.go_to_home)
            }
        }
    }
}
