package com.adyen.nexoapp.terminal

import com.adyen.nexoapp.R
import com.adyen.nexoapp.lib.model.terminal.TerminalModel

enum class TerminalItem(val terminalModel: TerminalModel, val nameRes: Int, val drawableRes: Int) {
    P400_PLUS(TerminalModel.P400_PLUS, R.string.p400_plus, R.drawable.p400_plus),
    V400C_PLUS(TerminalModel.V400C_PLUS, R.string.v400c_plus, R.drawable.v400c_plus),
    V240M_PLUS(TerminalModel.V240M_PLUS, R.string.v240m_plus, R.drawable.v240m_plus),
    V400M(TerminalModel.V400M, R.string.v400m, R.drawable.v400m)
}
