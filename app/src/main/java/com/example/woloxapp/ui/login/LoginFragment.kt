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
import com.example.woloxapp.model.User

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var _binding: LoginFragmentBinding
    private val binding get() = _binding
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
            loginViewModel.emptyFieldsError.observe(viewLifecycleOwner) {
                it?.let {
                    showToast(R.string.required_fields)
                }
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
            loginViewModel.validEmail.observe(viewLifecycleOwner) {
                if (it!!) fetchData(User(userName.text.toString(), password.text.toString()))
            }
        }
    }
    private fun fetchData(user: User) {
        loginViewModel.login(user)
        binding.progressBar.visibility = View.VISIBLE
        loginViewModel.credentialsOk.observe(viewLifecycleOwner) {
            if (it !== null) binding.progressBar.visibility = View.INVISIBLE
            when (it) {
                LoginViewModel.ResponseStatus.CredentialsOk -> this.findNavController()?.navigate(R.id.go_to_home)
                LoginViewModel.ResponseStatus.CredentialsFailure -> showToast(INVALID_CREDENTIALS)
                LoginViewModel.ResponseStatus.NetworkError -> showToast(CONNECTION_ERROR)
            }
        }
    }
    private fun showToast(string: Any) {
        val toastHorizontal = 0
        val toastVertical = 20
        val titleString = when (string) {
            is String -> string
            is Int -> getString(string)
            else -> throw IllegalAccessError()
        }
        val toast = Toast.makeText(context, titleString, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, toastHorizontal, toastVertical)
        toast.show()
    }

    companion object {
        const val CONNECTION_ERROR = "Connection Error"
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}
