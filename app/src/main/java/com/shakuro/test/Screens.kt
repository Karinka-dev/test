package com.shakuro.test

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.shakuro.test.model.User
import com.shakuro.test.ui.detail.DetailPhotoFragment
import com.shakuro.test.ui.main.UserFragment
import com.shakuro.test.utils.withArguments

object Screens {
    val user = FragmentScreen("UserFragment") { UserFragment() }
    fun userDetail(user: User) =
        FragmentScreen { DetailPhotoFragment().withArguments("user" to user) }

}
