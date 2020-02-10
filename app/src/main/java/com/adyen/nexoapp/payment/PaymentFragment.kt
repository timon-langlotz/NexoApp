package com.adyen.nexoapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.adyen.nexoapp.R
import com.adyen.nexoapp.util.getViewModelProvider
import kotlinx.android.synthetic.main.fragment_payment.amountEditText
import kotlinx.android.synthetic.main.fragment_payment.currencyEditText
import kotlinx.android.synthetic.main.fragment_payment.startPaymentButton
import kotlinx.android.synthetic.main.fragment_payment.stateTextView

class PaymentFragment : Fragment() {
    private val viewModel by lazy {
        val activity = requireActivity()
        val poiId = arguments!!.getString(ARG_POI_ID)!!
        val ipAddress = arguments!!.getString(ARG_IP_ADDRESS)!!
        val factory = PaymentViewModel.Factory(activity.application, poiId, ipAddress)
        getViewModelProvider(activity, factory).get(PaymentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.paymentStateLiveData.observe(viewLifecycleOwner, Observer { state: PaymentState? ->
            val text = when (state) {
                is IdleState -> getString(R.string.state_idle_status)
                is InProgressState -> getString(R.string.state_in_progress_status)
                is CompleteState -> getString(R.string.state_complete_status)
                is ErrorState -> getString(R.string.state_error_status_format, state.message)
                null -> getString(R.string.state_error_status_format)
            }
            stateTextView.text = text
        })

        startPaymentButton.setOnClickListener {
            val currency = currencyEditText.text.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (amount != null) {
                viewModel.sendPayment(currency, amount)
            } else {
                viewModel.postError("Invalid amount")
            }
        }
    }

    companion object {
        const val TAG = "PaymentFragment"
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
