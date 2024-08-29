package com.example.backgroundapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.backgroundapp.databinding.FragmentSignupBinding
import com.example.backgroundapp.viewmodel.SignupViewmodel

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel: SignupViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            signupViewModel.signUp(email, password)
        }

        signupViewModel.signupResultLiveData.observe(viewLifecycleOwner){ success ->
            if (success) {
                val action=SignupFragmentDirections.actionSignupFragmentToHomePageFragment()
                findNavController().navigate(action)
            }
        }

        signupViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}