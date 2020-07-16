package com.adyen.nexoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class TransactionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var poiId: String
    private lateinit var ipAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val sp: Spinner = findViewById(R.id.transaction_type_spinner)
        sp.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.transaction_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp.adapter = adapter
        }

        // Show first fragment in spinner
        showFragment("")
    }

    override fun onItemSelected(spinner: AdapterView<*>, view: View, pos: Int, id: Long) {
        Log.i("Spinner Selected", "" + spinner.getItemAtPosition(pos) + " selected")
        showFragment(spinner.getItemAtPosition(pos) as String)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Log.i("Spinner Selected", "Nothing selected")
    }

    private fun showFragment(fragmentName: String) {
        val visibleFragment = getVisibleFragment()
        lateinit var showFragmentName: String
        if (fragmentName.isEmpty()) {
            // When none is specified, make first item in array visible
            showFragmentName = resources.getStringArray(R.array.transaction_types)[0]
        } else {
            showFragmentName = fragmentName
        }

        // todo bit ugly reflection code.. find a way to find the classpath based on just the fragmentname
        val fragment: Fragment? =
            Class.forName("com.adyen.nexoapp." + showFragmentName.toLowerCase() + "." + showFragmentName + "Fragment")
                .newInstance() as? Fragment

        if (fragment != null) {
            if (!visibleFragment.isEmpty()) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.transaction_fragment, fragment, showFragmentName)
                    .addToBackStack(visibleFragment)
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.transaction_fragment, fragment, showFragmentName)
                    .commit()
            }
        }
    }

    private fun getVisibleFragment(): String {
        for (fragmentName in resources.getStringArray(R.array.transaction_types)) {
            val fragment = supportFragmentManager.findFragmentByTag(fragmentName)
            if ((fragment != null) && (fragment.isVisible)) {
                return fragmentName
            }
        }
        return ""
    }
}