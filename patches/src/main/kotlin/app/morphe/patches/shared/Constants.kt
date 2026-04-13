package app.morphe.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_QUICK_CURSOR =
            Compatibility(
                    name = "Quick Cursor",
                    packageName = "com.quickcursor",
                    apkFileType = ApkFileType.APK,
                    appIconColor = 0xba675d,
                    targets = listOf(AppTarget(version = null))
            )

    val COMPATIBILITY_TRUECALLER =
            Compatibility(
                    name = "Truecaller",
                    packageName = "com.truecaller",
                    apkFileType = ApkFileType.APK,
                    appIconColor = 0x0087ff,
                    targets = listOf(AppTarget(version = "26.12.5"))
            )
            
    val COMPATIBILITY_PINTEREST = Compatibility(
        name = "Pinterest",
        packageName = "com.pinterest",
        apkFileType = ApkFileType.APK,
        appIconColor = 0xaf3628,
        targets = listOf(
            AppTarget(version = "14.11.0")
        )
    )

    val COMPATIBILITY_ACALENDAR = Compatibility(
        name = "aCalendar",
        packageName = "org.withouthat.acalendar",
        apkFileType = ApkFileType.APK,
        appIconColor = 0x3a84c3,
        targets = listOf(
            AppTarget(version = null)
        )
    )
}
