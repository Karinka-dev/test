package com.shakuro.test.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.shakuro.test.MainStateEvent
import com.shakuro.test.Repository
import kotlinx.coroutines.launch

class DetailPhotoViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun setStateEvent(mainStateEvent: MainStateEvent) {

    }
}