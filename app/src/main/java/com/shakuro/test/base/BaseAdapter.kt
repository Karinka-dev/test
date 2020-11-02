package com.shakuro.test.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shakuro.test.R
import com.shakuro.test.User
import kotlinx.android.synthetic.main.layout_recycler_item.view.*

abstract class BaseAdapter(
    val watcher: Watcher
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    private var dataList: MutableList<User> = mutableListOf()
    val arrayList: ArrayList<User>
        get() = arrayListOf<User>().apply {
            dataList.forEach {
                add(it)
            }
        }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recycler_item, parent, false)
        return BaseViewHolder(view)
    }

    fun getIndexById(id: Int) = dataList.map { it.id }.lastIndexOf(id)

    fun setData(photos: List<User>) {
        dataList = photos.toMutableList()
        notifyDataSetChanged()
    }

    abstract fun onRemoveCompleted(id: Int)

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var users: User

        fun bind(users: User) {
            this.users = users
            with(itemView) {
                Glide.with(context)
                    .load(users.avatar_url).centerCrop()
                    .into(userImage)
                userName.text = users.login
                userId.text = users.id.toString()
            }
            setListeners()
        }

        private fun setListeners() {
            itemView.setOnClickListener {
                watcher.showDetailedScreenUsers(adapterPosition)
            }
        }
    }
}
