package com.example.backgroundapp.di

import com.example.backgroundapp.network.Interceptor
import com.example.backgroundapp.network.UnsplashService
import com.example.backgroundapp.util.Constants
import com.example.backgroundapp.viewmodel.CategoryViewModel
import com.example.backgroundapp.viewmodel.DetailViewModel
import com.example.backgroundapp.viewmodel.LoginViewModel
import com.example.backgroundapp.viewmodel.PhotoViewModel
import com.example.backgroundapp.viewmodel.ProfileViewModel
import com.example.backgroundapp.viewmodel.SearchViewModel
import com.example.backgroundapp.viewmodel.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { FirebaseFirestore.getInstance() }

    single { FirebaseAuth.getInstance() }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(Interceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(UnsplashService::class.java)
    }

    viewModel { CategoryViewModel(get(), get(), androidApplication()) }

    viewModel { PhotoViewModel(androidApplication(), get(), get(), get()) }

    viewModel { SignupViewModel(androidApplication(), get(), get()) }

    viewModel { LoginViewModel(androidApplication(), get()) }

    viewModel { SearchViewModel(androidApplication(), get()) }

    viewModel { DetailViewModel(androidApplication(), get(), get(),get()) }

    viewModel { ProfileViewModel(androidApplication(),get(),get()) }

}