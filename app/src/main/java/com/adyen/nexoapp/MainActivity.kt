package com.adyen.nexoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adyen.nexoapp.terminal.select.SelectTerminalFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SelectTerminalFragment(), SelectTerminalFragment.TAG)
                .commit()
        }
    }
}
