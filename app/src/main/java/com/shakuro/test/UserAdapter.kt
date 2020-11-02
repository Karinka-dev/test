package com.shakuro.test

import com.shakuro.test.base.BaseAdapter
import com.shakuro.test.base.Watcher

class UsersAdapter(
    watcher: Watcher
) : BaseAdapter(watcher) {

    override fun onRemoveCompleted(id: Int) {
        notifyItemChanged(getIndexById(id))
    }

    fun onAddCompleted(id: Int) {
        notifyItemChanged(getIndexById(id))
    }

}