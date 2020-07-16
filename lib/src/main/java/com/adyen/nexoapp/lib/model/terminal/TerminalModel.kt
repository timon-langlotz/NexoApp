package com.adyen.nexoapp.lib.model.terminal

enum class TerminalModel(val model: String, val serialLength: Int) {
    P400_PLUS("P400Plus", 9),
    V240M_PLUS("V240mPlus", 9),
    V400C_PLUS("V400cPlus", 9),
    V400M("V400m", 9);
}
