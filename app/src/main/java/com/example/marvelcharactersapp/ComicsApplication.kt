package com.example.marvelcharactersapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Application() is base class for implementing global Application state.
@HiltAndroidApp
class ComicsApplication : Application() {

}