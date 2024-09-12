package com.example.backgroundapp.view

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.backgroundapp.R
import com.example.backgroundapp.databinding.FragmentDetailBinding
import com.example.backgroundapp.util.loadCircleImage
import com.example.backgroundapp.util.loadImage
import com.example.backgroundapp.viewmodel.DetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.net.HttpURLConnection
import java.net.URL

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by inject()
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewModel.getImageDetail(id = args.id)

        setupSaveButton()
        observe()

        binding.imageBottom.setOnClickListener {
            showBottomSheet()
        }
        binding.imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun observe() {
        with(binding) {
            detailViewModel.imageResponse.observe(viewLifecycleOwner) { image ->
                if (image != null) {
                    image.urls?.full?.let { url ->
                        backgroundImageView.loadImage(url)
                        imageUrl = url
                    }
                    image.user?.profileImage?.small?.let { profileImage ->
                        imageProfile.loadCircleImage(profileImage)
                    }
                    textName.text = image.user?.name.orEmpty()
                    textLike.text = image.likes?.toString().orEmpty()
                    detailViewModel.checkIfPhotoIsSaved(image.urls?.full.orEmpty())
                }
            }
            detailViewModel.isPhotoSavedLiveData.observe(viewLifecycleOwner) { isSaved ->
                setButtonSavedState(isSaved)
            }

            detailViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
                progressBar.isVisible = loading
            }
            detailViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setButtonSavedState(isSaved: Boolean) {
        binding.imageViewSave.apply {
            setImageResource(
                if (isSaved) R.drawable.baseline_bookmark_blue else R.drawable.baseline_bookmark_24
            )
        }
    }

    private fun setupSaveButton() {
        binding.imageViewSave.setOnClickListener {
            val photo = detailViewModel.imageResponse.value
            photo?.let {
                if (detailViewModel.isPhotoSavedLiveData.value == false) {
                    detailViewModel.saveImageProfile(it)
                }
            }
        }
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(view)

        val menu = view.findViewById<NavigationView>(R.id.bottomNavView)
        menu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_download -> {
                    imageUrl?.let { downloadImage(it) }
                    bottomSheetDialog.dismiss()
                    true
                }

                R.id.setHomeWallpaper -> {
                    imageUrl?.let { setWallpaper(it, WallpaperManager.FLAG_SYSTEM) }
                    bottomSheetDialog.dismiss()
                    true
                }

                R.id.setLockWallpaper -> {
                    imageUrl?.let { setWallpaper(it, WallpaperManager.FLAG_LOCK) }
                    bottomSheetDialog.dismiss()
                    true
                }

                R.id.setLockHomeWallpaper -> {
                    imageUrl?.let {
                        setWallpaper(
                            it,
                            WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                        )
                    }
                    bottomSheetDialog.dismiss()
                    true
                }

                else -> false
            }
        }
        bottomSheetDialog.show()
    }

    private fun downloadImage(imageUrl: String) {
        val request = DownloadManager.Request(Uri.parse(imageUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(getString(R.string.downloading_image))
        request.setDescription(getString(R.string.downloading_image))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "unsplash_image.jpg"
        )
        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private fun setWallpaper(imageUrl: String, flag: Int) {
        val wallpaperManager = WallpaperManager.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.inputStream.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    withContext(Dispatchers.Main) {
                        wallpaperManager.setBitmap(bitmap, null, true, flag)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.set_wallpaper),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_wallpaper),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
