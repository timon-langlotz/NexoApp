package com.adyen.nexoapp.refundtoacard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.adyen.nexoapp.*
import com.adyen.nexoapp.util.getViewModelProvider
import kotlinx.android.synthetic.main.fragment_payment.amountEditText
import kotlinx.android.synthetic.main.fragment_payment.currencyEditText
import kotlinx.android.synthetic.main.fragment_payment.stateTextView
import kotlinx.android.synthetic.main.refund_to_a_card_fragment.*

class RefundToACardFragment : Fragment() {
    private val viewModel by lazy {
        val activity = requireActivity()
        val factory = RefundToACardViewModel.Factory(activity.application)
        getViewModelProvider(activity, factory).get(RefundToACardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.refund_to_a_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.transactionStateLiveData.observe(
            viewLifecycleOwner,
            Observer { state: TransactionState? ->
                val text = when (state) {
                    is IdleState -> getString(R.string.state_idle_status)
                    is InProgressState -> getString(R.string.state_in_progress_status)
                    is CompleteState -> getString(R.string.state_complete_status)
                    is ErrorState -> getString(R.string.state_error_status_format, state.message)
                    null -> getString(R.string.state_error_status_format)
                }
                stateTextView.text = text
            })

        refundButton.setOnClickListener {
            val currency = if (currencyEditText.text.toString()
                    .isEmpty()
            ) getString(R.string.currency_hint) else currencyEditText.text.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (amount != null) {
                viewModel.startRefund(currency, amount)
            } else {
                viewModel.postError("Invalid amount")
            }
        }

    }

    companion object {
        const val TAG = "Payment"

        fun newInstance(): RefundToACardFragment {
            return RefundToACardFragment()
        }
    }

}