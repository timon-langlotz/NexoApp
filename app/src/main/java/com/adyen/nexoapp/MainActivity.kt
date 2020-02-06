package com.adyen.nexoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.adyen.nexoapp.payment.PaymentFragment
import com.adyen.nexoapp.terminal.TerminalItem
import com.adyen.nexoapp.terminal.select.SelectTerminalFragment
import com.adyen.nexoapp.terminal.details.TerminalDetailsFragment

class MainActivity : AppCompatActivity(), SelectTerminalFragment.Listener, TerminalDetailsFragment.Listener{
    override fun onTerminalItemSelected(terminalItem: TerminalItem) {
        val enterSerialFragment = TerminalDetailsFragment.newInstance(terminalItem)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, enterSerialFragment, TerminalDetailsFragment.TAG)
            .addToBackStack(TerminalDetailsFragment.TAG)
            .commit()
    }

    override fun onTerminalConfigured(terminalItem: TerminalItem, serialNumber: String, ipAddress: String) {
        val poiId = try {
            terminalItem.terminalModel.createPoiId(serialNumber)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return
        }

        val paymentFragment = PaymentFragment.newInstance(poiId, ipAddress)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, paymentFragment, PaymentFragment.TAG)
            .addToBackStack(TerminalDetailsFragment.TAG)
            .commit()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        when (fragment) {
            is SelectTerminalFragment -> fragment.listener = this
            is TerminalDetailsFragment -> fragment.listener = this
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
