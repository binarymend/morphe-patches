package app.morphe.patches.symfonium

import app.morphe.patcher.Fingerprint

object Fingerprints {
    // FirebaseManager (ab3)
    val FirebaseManagerFingerprint = Fingerprint(definingClass = "Lab3;")

    // isPremium method (m)
    val IsPremiumMethodFingerprint =
            Fingerprint(definingClass = "Lab3;", name = "m", returnType = "Z")

    // PremiumState (s65)
    val PremiumStateClassFingerprint = Fingerprint(definingClass = "Ls65;")
}
