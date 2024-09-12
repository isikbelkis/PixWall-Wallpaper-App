package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.backgroundapp.databinding.FragmentSignupBinding
import com.example.backgroundapp.viewmodel.SignupViewModel
import org.koin.android.ext.android.inject

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel: SignupViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonSignup.setOnClickListener {
                val firstName = editTextName.text.toString()
                val lastName = editTextLastName.text.toString()
                val userName = editTextUserName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                signupViewModel.signUp(firstName, lastName, userName, email, password)
            }
        }

        signupViewModel.signupResultLiveData.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action = SignupFragmentDirections.actionSignupFragmentToCategoryFragment()
                findNavController().navigate(action)
            }
        }

        signupViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}