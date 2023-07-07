package com.example.marvelcharactersapp

import com.example.marvelcharactersapp.model.api.ApiService
import com.example.marvelcharactersapp.model.api.MarvelApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideapiRepo() = MarvelApiRepo(ApiService.api)
//    It will inject the MarvelApiRepo into the ViewModel based on the ApiService we have defined.
}