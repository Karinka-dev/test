package com.shakuro.test.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.shakuro.test.network.Repository

class DetailPhotoViewModel
@ViewModelInject
constructor(
    private val router: Router,
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun onBackPressed() {
        router.exit()
    }
}