package com.shakuro.test.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shakuro.test.R
import com.shakuro.test.model.User
import com.shakuro.test.ui.main.UserFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail_photo.*

class DetailPhotoFragment : Fragment(R.layout.fragment_detail_photo) {

    private val viewModel: DetailPhotoViewModel by viewModels()

    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = requireArguments().getParcelable(UserFragment.USER)!!
        activity?.toolbar?.navigationIcon = resources.getDrawable(R.drawable.ic_chevron_back)
        activity?.toolbar?.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
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
