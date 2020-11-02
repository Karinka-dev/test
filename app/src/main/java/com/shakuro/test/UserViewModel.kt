package com.shakuro.test

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach

class UserViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var list = mutableListOf<User>()
    private val mutableUsers = MutableLiveData<DataState<List<User>>>()
    val users: LiveData<DataState<List<User>>>
        get() = mutableUsers

    private var pages = 0

    init {
        setStateEvent(MainStateEvent.GetUsersEvent)
    }

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetUsersEvent -> {
                    mainRepository.getUsers(pages) // need change logic paging
                        .onEach { dataState ->
                            subscribeDataState(dataState)
                        }.launchIn(viewModelScope)
                }
                is MainStateEvent.ClearPage -> {
                    pages = 0
                    setStateEvent(MainStateEvent.GetUsersEvent)
                }
                is MainStateEvent.AddPage -> {
                    pages += 30
                    setStateEvent(MainStateEvent.GetUsersEvent)
                }
            }
        }
    }

    private fun subscribeDataState(dataState: DataState<List<User>>) {
        when (dataState) {
            is DataState.Loading -> {
                mutableUsers.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mutableUsers.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                if (pages == 0)
                    list.clear()
                list.addAll(dataState.data)
                mutableUsers.postValue(DataState.Success(list))
            }
        }
    }
}