package com.adyen.nexoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.adyen.nexoapp.lib.model.terminal.TerminalModel
import com.adyen.nexoapp.terminal.select.SelectTerminalFragment

class MainActivity : AppCompatActivity(), SelectTerminalFragment.Listener {
    override fun onTerminalModelSelected(terminalModel: TerminalModel) {
        println("$terminalModel selected")
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is SelectTerminalFragment) {
            fragment.listener = this
        }
    }

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
