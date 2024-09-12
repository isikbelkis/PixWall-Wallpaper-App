package com.example.backgroundapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.backgroundapp.R
import com.example.backgroundapp.adapter.PhotoAdapter
import com.example.backgroundapp.databinding.FragmentProfileBinding
import com.example.backgroundapp.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by inject()
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerSave.layoutManager = layoutManager

        adapter = PhotoAdapter(
            emptyList(),
            onClick = { photo ->
                val action =
                    ProfileFragmentDirections.actionProfileFragment2ToDetailFragment2(id = photo.id!!)
                findNavController().navigate(action)
            }
        )
        binding.recyclerSave.adapter = adapter

        profileViewModel.getUserData()
        profileViewModel.getSavedPhotos()

        profileViewModel.userDataLiveData.observe(viewLifecycleOwner) { userMap ->
            val firstName = userMap["firstName"] ?: ""
            val lastName = userMap["lastName"] ?: ""
            val fullName = "$firstName $lastName"
            binding.textViewName.text = fullName
            binding.textViewUsername.text = userMap["username"]
            binding.textViewEmail.text = userMap["email"]
        }

        profileViewModel.savedPhotosLiveData.observe(viewLifecycleOwner) { photos ->
            if (photos != null && photos.isNotEmpty()) {
                adapter.updatePhotos(photos)
            }
        }

        profileViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageLogout.setOnClickListener {
            bottomSheetLogout()
        }

        return binding.root
    }

    private fun bottomSheetLogout() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_logout, null)
        bottomSheetDialog.setContentView(view)

        val menu = view.findViewById<NavigationView>(R.id.bottomNavOnboarding)
        menu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(requireContext(), LoginFragment::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    bottomSheetDialog.dismiss()

                    true
                }

                else -> false
            }
        }
        bottomSheetDialog.show()
    }
}