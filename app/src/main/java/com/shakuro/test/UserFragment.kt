package com.shakuro.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.shakuro.test.base.Watcher
import com.shakuro.test.ui.detail.DetailPhotoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_users.*

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_users), Watcher {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var adapter: UsersAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setTitle("Фотографии")
        initList()
        Glide.get(requireContext()).setMemoryCategory(MemoryCategory.HIGH)
        swipe_container.setOnRefreshListener {
            swipe_container.isRefreshing = false
            viewModel.setStateEvent(MainStateEvent.ClearPage)
        }
        subscribe()
    }

    private fun initList() {
        adapter = UsersAdapter(this)
        layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (viewModel.users.value !is DataState.Loading && totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.setStateEvent(MainStateEvent.AddPage)
                    Log.d("GGGG", "onScrollStateChanged: ")
                }

            }
        })
    }

    private fun subscribe() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
                is DataState.Error -> {
                    progress_bar.visibility = View.GONE
                    showAlertDialog(it.exception.localizedMessage) {
                        viewModel.setStateEvent(MainStateEvent.GetUsersEvent)
                    }
                }
                is DataState.Success -> {
                    progress_bar.visibility = View.GONE
                    adapter.setData(it.data)
                }
            }
        })
    }

    override fun showDetailedScreenUsers(position: Int) {
//        changeVisibilityBnvAndToolBar(false)
        val bundle = Bundle().apply {
            putParcelable(USER, adapter.arrayList[position])
        }
        val fragment: DetailPhotoFragment = DetailPhotoFragment().apply {
            arguments = bundle
        }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.mainContainer, fragment)?.addToBackStack(null)?.commit()
    }

    companion object {
        const val USER = "user"
    }
}
