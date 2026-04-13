package app.morphe.patches.acalendar

import app.morphe.patcher.Fingerprint

object Fingerprints {
    val isPurchased = Fingerprint(
        definingClass = "Lde/tapirapps/calendarmain/I3;",
        name = "L",
        returnType = "Z",
        parameters = listOf("Ljava/lang/String;")
    )
}
