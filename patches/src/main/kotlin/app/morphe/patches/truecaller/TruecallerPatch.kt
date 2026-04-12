package app.morphe.patches.truecaller

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants
import app.morphe.patches.shared.SmaliTemplates
import app.morphe.patches.truecaller.Fingerprints.AppStartTrackerFingerprint
import app.morphe.patches.truecaller.Fingerprints.AdsBazFingerprint

@Suppress("unused")
val truecallerNoTelemetryPatch = bytecodePatch(
    name = "Disable Telemetry",
    description = "Disables AppStartTracker and other telemetry points.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY_TRUECALLER)

    execute {
        val trackerClass = classDefBy(AppStartTrackerFingerprint.definingClass!!)
        val mutableTrackerClass = AppStartTrackerFingerprint.match(trackerClass).classDef
        for (method in mutableTrackerClass.methods) {
            if (method.name.contains("log") || method.name == "enableTracking") {
                if (method.implementation == null) continue
                method.removeInstructions(0, method.instructions.count())
                method.addInstructions(0, SmaliTemplates.returnVoid())
            }
        }
    }
}

@Suppress("unused")
val truecallerNoAdsPatch = bytecodePatch(
    name = "Remove Ads",
    description = "Disables all ad feature toggles globally.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY_TRUECALLER)

    execute {
        val adsClass = classDefBy(AdsBazFingerprint.definingClass!!)
        val mutableAdsClass = AdsBazFingerprint.match(adsClass).classDef
        
        for (method in mutableAdsClass.methods) {
            // All boolean methods in baz/bar should return false to disable features
            if (method.returnType == "Z") {
                if (method.implementation == null) continue
                method.removeInstructions(0, method.instructions.count())
                method.addInstructions(0, SmaliTemplates.returnBoolean(false))
            }
        }
    }
}
