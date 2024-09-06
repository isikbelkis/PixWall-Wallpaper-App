package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.backgroundapp.adapter.PhotoAdapter
import com.example.backgroundapp.databinding.FragmentHomePageBinding
import com.example.backgroundapp.viewmodel.PhotoViewModel
import org.koin.android.ext.android.inject

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModel: PhotoViewModel by inject()
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recylerView.layoutManager = layoutManager

        viewModel.photosLiveData.observe(viewLifecycleOwner) { photos ->
            adapter = PhotoAdapter(photos, onClick = { photoItem ->
                Toast.makeText(context, "Fotoğraf tıklandı: ${photoItem.id}", Toast.LENGTH_SHORT).show()
            })
            binding.recylerView.adapter=adapter
        }

        viewModel.loadCategoriesAndFetchPhotos()

        return binding.root
    }
}
