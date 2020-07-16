package com.adyen.nexoapp.terminal.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.adyen.nexoapp.R
import com.adyen.nexoapp.terminal.SelectedTerminalInfo
import com.adyen.nexoapp.terminal.TerminalItem
import com.adyen.nexoapp.util.WifiUtil
import kotlinx.android.synthetic.main.fragment_terminal_details.ipAddressEditText
import kotlinx.android.synthetic.main.fragment_terminal_details.serialNumberEditText
import kotlinx.android.synthetic.main.fragment_terminal_details.terminalImageView
import kotlinx.android.synthetic.main.fragment_terminal_details.terminalNameTextView

class TerminalDetailsFragment : Fragment() {
    private val terminalItem by lazy { arguments!!.getSerializable(ARG_TERMINAL_ITEM) as TerminalItem }

    var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terminal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            ipAddressEditText.setText(WifiUtil.getNetworkAddress(view.context))
            ipAddressEditText.setSelection(ipAddressEditText.length())
        }

        terminalImageView.setImageResource(terminalItem.drawableRes)
        terminalNameTextView.setText(terminalItem.nameRes)
        ipAddressEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val serialNumber = serialNumberEditText.text.toString()
                val ipAddress = ipAddressEditText.text.toString()
                listener?.onTerminalConfigured(terminalItem, serialNumber, ipAddress)
                true
            } else {
                false
            }
        }
    }

    companion object {
        const val TAG = "EnterSerialFragment"

        private const val ARG_TERMINAL_ITEM = "ARG_TERMINAL_ITEM"

        fun newInstance(terminalItem: TerminalItem): TerminalDetailsFragment {
            return TerminalDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TERMINAL_ITEM, terminalItem)
                }
            }
        }
    }

    interface Listener {
        fun onTerminalConfigured(terminalItem: TerminalItem, serialNumber: String, ipAddress: String)
    }
}
