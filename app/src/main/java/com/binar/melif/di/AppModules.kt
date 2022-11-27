package com.binar.melif.di

import com.binar.melif.BuildConfig
import com.binar.melif.data.network.api.datasource.TvShowApiDataSource
import com.binar.melif.data.network.api.datasource.TvShowDataSourceImpl
import com.binar.melif.data.network.service.TvShowApiService
import com.binar.melif.data.repository.TvShowRepository
import com.binar.melif.data.repository.TvShowRepositoryImpl
import com.binar.melif.presentation.adapter.TvShowAdapter
import com.binar.melif.presentation.ui.tvshow.TvShowViewModel
import com.binar.melif.presentation.ui.webdetail.WebDetailViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    fun getModules(): List<Module> = listOf(
         common, firebase, networkModule, dataSource, repository, viewModels, adapter
    )

    private val common = module {
        single { Gson() }
    }

    private val firebase = module {
        single { FirebaseAuth.getInstance() }
        single { params ->
            GoogleSignIn.getClient(
                params.get(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.FIREBASE_WEB_CLIENT_ID)
                    .requestEmail()
                    .build()
            )
        }
    }

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { TvShowApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<TvShowApiDataSource> { TvShowDataSourceImpl(get()) } // singleton
    }

    private val repository = module {
        single<TvShowRepository> { TvShowRepositoryImpl(get()) } // singleton
    }

    private val viewModels = module {
        viewModel { TvShowViewModel(get()) }

    }

    private val adapter = module {
        factory { TvShowAdapter() }
    }

}