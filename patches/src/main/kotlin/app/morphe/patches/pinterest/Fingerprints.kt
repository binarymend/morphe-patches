package app.morphe.patches.pinterest

import app.morphe.patcher.Fingerprint

object Fingerprints {
    // qu.c -> d0(yd)
    val AdUtilsFingerprint = Fingerprint(
        definingClass = "Lqu/c;",
        name = "d0"
    )

    // yd -> B5() (is_promoted)
    val IsPromotedFingerprint = Fingerprint(
        definingClass = "Lcom/pinterest/api/model/yd;",
        name = "B5"
    )

    // yd -> d5() (is_active_ad)
    val IsActiveAdFingerprint = Fingerprint(
        definingClass = "Lcom/pinterest/api/model/yd;",
        name = "d5"
    )

    val BugsnagFingerprint = Fingerprint(
        definingClass = "Ld9/c;",
        strings = listOf("2bf6075d2aea98d30d4c992f2d8df241")
    )

    val TelemetryTaskFingerprint = Fingerprint(
        definingClass = "Lg10/n4;",
        name = "c"
    )
}
