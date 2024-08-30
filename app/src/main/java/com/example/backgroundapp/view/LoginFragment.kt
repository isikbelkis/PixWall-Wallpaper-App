package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.backgroundapp.databinding.FragmentLoginBinding
import com.example.backgroundapp.viewmodel.LoginViewmodel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewmodel:LoginViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLoginBinding.inflate(inflater,container,false)

        binding.buttonLogin.setOnClickListener {
            val email=binding.editTextEmail.text.toString()
            val password=binding.editTextPassword.text.toString()
            loginViewmodel.login(email,password)
        }
        binding.buttonSignup.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)
        }

        loginViewmodel.loginResultLiveData.observe(viewLifecycleOwner){succes->
            if (succes){
                val action=LoginFragmentDirections.actionLoginFragmentToHomePageFragment()
                findNavController().navigate(action)
            }
        }

        loginViewmodel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}