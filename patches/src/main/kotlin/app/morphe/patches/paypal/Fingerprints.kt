package app.morphe.patches.paypal

import app.morphe.patcher.Fingerprint

object Fingerprints {
    val getBalance = Fingerprint(
        definingClass = "Lcom/paypal/android/p2pmobile/MoneyManager;",
        name = "getCurrentBalance",
        returnType = "J"
    )

    val isBroke = Fingerprint(
        definingClass = "Lcom/paypal/android/p2pmobile/UserStatus;",
        name = "isUserBroke",
        returnType = "Z"
    )

    val getInfiniteMoney = Fingerprint(
        definingClass = "Lcom/paypal/android/p2pmobile/AprilFools;",
        name = "getInfiniteMoney",
        returnType = "V"
    )
}
