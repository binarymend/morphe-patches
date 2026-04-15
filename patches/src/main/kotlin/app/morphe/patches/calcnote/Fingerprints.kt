package app.morphe.patches.calcnote

import app.morphe.patcher.Fingerprint

object Fingerprints {
    val TE5_F = Fingerprint(
        definingClass = "Lte5;",
        name = "f",
        parameters = emptyList(),
        returnType = "Z"
    )

    val TE5_E = Fingerprint(
        definingClass = "Lte5;",
        name = "e",
        parameters = listOf("Lcom/revenuecat/purchases/CustomerInfo;"),
        returnType = "Z"
    )

    val TE5_B = Fingerprint(
        definingClass = "Lte5;",
        name = "b",
        parameters = emptyList(),
        returnType = "Z"
    )

    val TE5_D = Fingerprint(
        definingClass = "Lte5;",
        name = "d",
        parameters = emptyList(),
        returnType = "Z"
    )

    val TE5_INIT = Fingerprint(
        definingClass = "Lte5;",
        name = "<init>",
        parameters = emptyList(),
        returnType = "V"
    )
}
