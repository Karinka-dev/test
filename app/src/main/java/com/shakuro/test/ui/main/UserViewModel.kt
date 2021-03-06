package com.shakuro.test.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.terrakok.cicerone.Router
import com.shakuro.test.Screens
import com.shakuro.test.model.User
import com.shakuro.test.network.Repository
import com.shakuro.test.utils.DataState
import com.shakuro.test.utils.MainStateEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserViewModel
@ViewModelInject
constructor(
    private val router: Router,
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

    fun onOpenNewScreen(user: User) {
        router.navigateTo(Screens.userDetail(user))
    }

    fun onBackPressed() {
        router.exit()
    }
}