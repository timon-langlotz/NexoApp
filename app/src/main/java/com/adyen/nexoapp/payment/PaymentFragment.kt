package com.adyen.nexoapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adyen.nexoapp.util.getViewModelProvider

class PaymentFragment : Fragment() {
    private val viewModel by lazy {
        val activity = requireActivity()
        val poiId = arguments!!.getString(ARG_POI_ID)!!
        val ipAddress = arguments!!.getString(ARG_IP_ADDRESS)!!
        val factory = PaymentViewModel.Factory(activity.application, poiId, ipAddress)
        getViewModelProvider(activity, factory).get(PaymentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val ARG_POI_ID = "ARG_POI_ID"
        private const val ARG_IP_ADDRESS = "ARG_IP_ADDRESS"

        fun newInstance(poiId: String, ipAddress: String): PaymentFragment {
            return PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POI_ID, poiId)
                    putString(ARG_IP_ADDRESS, ipAddress)
                }
            }
        }
    }
}
