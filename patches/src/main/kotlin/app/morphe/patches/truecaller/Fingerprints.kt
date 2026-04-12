package app.morphe.patches.truecaller

import app.morphe.patcher.Fingerprint

object Fingerprints {
    // Lcom/truecaller/analytics/technical/AppStartTracker;
    val AppStartTrackerFingerprint = Fingerprint(
        definingClass = "Lcom/truecaller/analytics/technical/AppStartTracker;"
    )

    // Loi1/baz;
    val AdsBazFingerprint = Fingerprint(
        definingClass = "Loi1/baz;"
    )

    // Lcom/truecaller/bottombar/BottomBarView$BottomBarMenuItemId;
    val BottomBarMenuItemIdFingerprint = Fingerprint(
        definingClass = "Lcom/truecaller/bottombar/BottomBarView\$BottomBarMenuItemId;",
        name = "\$values"
    )
}
