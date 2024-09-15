package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.backgroundapp.databinding.FragmentLoginBinding
import com.example.backgroundapp.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewmodel: LoginViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        observe()

        return binding.root
    }

    private fun observe() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                loginViewmodel.login(email, password)
            }
            buttonSignup.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                findNavController().navigate(action)
            }
            loginViewmodel.loginResultLiveData.observe(viewLifecycleOwner) { succes ->
                if (succes) {
                    val action = LoginFragmentDirections.actionLoginFragmentToBattomNavActivity()
                    findNavController().navigate(action)
                }
            }
            loginViewmodel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}