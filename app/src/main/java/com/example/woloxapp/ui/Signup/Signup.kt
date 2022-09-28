package com.example.woloxapp.ui.Signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentSignupBinding

class Signup : Fragment() {
    lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        with(binding) {
            backLoginBtn.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
        }
        return binding.root
    }
}
