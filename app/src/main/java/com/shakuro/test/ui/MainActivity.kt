package com.shakuro.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakuro.test.R
import com.shakuro.test.ui.main.UserFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar_title.text = getString(R.string.app_name)
        supportFragmentManager.beginTransaction().replace(
            R.id.mainContainer,
            UserFragment()
        ).commitNow()
    }
}
