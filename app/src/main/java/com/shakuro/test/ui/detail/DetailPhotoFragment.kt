package com.shakuro.test.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shakuro.test.R
import com.shakuro.test.User
import com.shakuro.test.UserFragment
import kotlinx.android.synthetic.main.fragment_detail_photo.*

class DetailPhotoFragment : Fragment(R.layout.fragment_detail_photo) {

    private val viewModel: DetailPhotoViewModel by viewModels()

    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = requireArguments().getParcelable(UserFragment.USER)!!
        initView()
    }

    private fun initView() {
        with(activity) {
            com.bumptech.glide.Glide.with(requireContext())
                .load(user.avatar_url).centerCrop()
                .into(userImage)
            userName.text = user.login
        }
    }
}
