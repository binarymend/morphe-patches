package app.morphe.patches.pinterest

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patches.pinterest.Fingerprints.AdUtilsFingerprint
import app.morphe.patches.pinterest.Fingerprints.BugsnagFingerprint
import app.morphe.patches.pinterest.Fingerprints.IsActiveAdFingerprint
import app.morphe.patches.pinterest.Fingerprints.IsPromotedFingerprint
import app.morphe.patches.pinterest.Fingerprints.TelemetryTaskFingerprint
import app.morphe.patches.shared.Constants.COMPATIBILITY_PINTEREST
import app.morphe.patches.shared.SmaliTemplates

@Suppress("unused")
val pinterestAdsPatch = bytecodePatch(
    name = "Remove Promoted Pins",
    description = "Disables ad detection logic globally. Safe for Home feed.",
    default = true
) {
    compatibleWith(COMPATIBILITY_PINTEREST)

    execute {
        // 1. Force AdUtils.d0 to return false
        val adUtilsClass = classDefBy(AdUtilsFingerprint.definingClass!!)
        val d0Match = AdUtilsFingerprint.match(adUtilsClass)
        d0Match.method.apply {
            implementation?.let { impl ->
                removeInstructions(0, impl.instructions.count())
                addInstructions(0, SmaliTemplates.returnBoolean(false))
            }
        }

        // 2. Force yd.B5 (is_promoted) to return Boolean.FALSE
        val ydClass = classDefBy(IsPromotedFingerprint.definingClass!!)
        val b5Match = IsPromotedFingerprint.match(ydClass)
        b5Match.method.apply {
            implementation?.let { impl ->
                removeInstructions(0, impl.instructions.count())
                addInstructions(0, SmaliTemplates.returnFalseObject())
            }
        }

        // 3. Force yd.d5 (is_active_ad) to return Boolean.FALSE
        val d5Match = IsActiveAdFingerprint.match(ydClass)
        d5Match.method.apply {
            implementation?.let { impl ->
                removeInstructions(0, impl.instructions.count())
                addInstructions(0, SmaliTemplates.returnFalseObject())
            }
        }
    }
}

@Suppress("unused")
val pinterestTelemetryPatch = bytecodePatch(
    name = "Disable Bugsnag Telemetry",
    description = "Disables Bugsnag crash reporting and analytics.",
    default = true
) {
    compatibleWith(COMPATIBILITY_PINTEREST)

    execute {
        val targetClass = classDefBy(BugsnagFingerprint.definingClass!!)
        val match = BugsnagFingerprint.match(targetClass)
        val mutableClass = match.classDef

        val methodNames = listOf("h", "p", "i", "k")
        methodNames.forEach { name ->
            mutableClass.methods.firstOrNull { it.name == name }?.let { method ->
                val impl = method.implementation ?: return@let
                method.removeInstructions(0, impl.instructions.count())
                method.addInstruction(0, SmaliTemplates.returnVoid())
            }
        }

        mutableClass.methods.firstOrNull { it.name == "g" }?.let { method ->
            val impl = method.implementation ?: return@let
            method.removeInstructions(0, impl.instructions.count())
            method.addInstructions(0, SmaliTemplates.returnNullObject())
        }
    }
}

@Suppress("unused")
val pinterestGeneralTelemetryPatch = bytecodePatch(
    name = "Disable General Telemetry",
    description = "Disables various analytics and tracking tasks.",
    default = true
) {
    compatibleWith(COMPATIBILITY_PINTEREST)

    execute {
        val targetClass = classDefBy(TelemetryTaskFingerprint.definingClass!!)
        val match = TelemetryTaskFingerprint.match(targetClass)
        val method = match.method

        if (method.implementation == null) return@execute

        val tagsToBlock = listOf(
            "TAG_CRASH_REPORTING",
            "TAG_APPSFLYER_INIT",
            "TAG_FIREBASE_ANALYTICS_INIT",
            "TAG_GOOGLE_ENGAGE_INIT",
            "TAG_ADS_GMA_MANAGER_INIT",
            "TAG_ADS_OPEN_MEASUREMENT_SDK_INIT",
            "TAG_TRACKING_REQUESTS",
            "TAG_RUM_REPORTING",
            "TAG_SCHEDULE_SUBMIT_NETWORK_METRICS",
            "TAG_LANDING_SIGNALS_UPLOAD",
            "TAG_ADS_APP_INSTALL_LOG",
            "TAG_LOG_DEVICE_PROFILE",
            "TAG_LOG_ENTRY_POINT",
            "TAG_LOG_LOCATION_PERMISSIONS"
        )

        val sb = StringBuilder()
        sb.append("move-object/from16 v2, p0\n")
        sb.append("iget-object v1, v2, Lg10/n4;->b:Lg10/w;\n")
        tagsToBlock.forEachIndexed { i, tag ->
            sb.append("sget-object v0, Lg10/w;->$tag:Lg10/w;\n")
            sb.append("if-ne v1, v0, :cond_next_$i\n")
            sb.append("return-void\n")
            sb.append(":cond_next_$i\n")
        }
        sb.append("nop\n")

        method.addInstructions(0, sb.toString())
    }
}
