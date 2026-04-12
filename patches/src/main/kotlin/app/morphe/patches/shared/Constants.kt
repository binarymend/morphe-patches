package app.morphe.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_QUICK_CURSOR = Compatibility(
        name = "Quick Cursor",
        packageName = "com.quickcursor",
        apkFileType = ApkFileType.APK,
        appIconColor = 0xba675d,
        targets = listOf(
            AppTarget(version = null)
        )
    )
}
