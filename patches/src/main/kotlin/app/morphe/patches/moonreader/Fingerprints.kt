package app.morphe.patches.moonreader

import app.morphe.patcher.Fingerprint

object Fingerprints {
    val IS_PRO =
            Fingerprint(
                    definingClass = "Lcom/flyersoft/tools/A;",
                    name = "isPro",
                    parameters = emptyList(),
                    returnType = "Z"
            )

    val LOAD_OPTIONS =
            Fingerprint(
                    definingClass = "Lcom/flyersoft/tools/A;",
                    name = "LoadOptions",
                    parameters = listOf("Landroid/content/Context;"),
                    returnType = "V"
            )

    // Class: com.radaee.pdf.Global - Init with Context only
    val PDF_INIT =
            Fingerprint(
                    definingClass = "Lcom/radaee/pdf/Global;",
                    name = "Init",
                    parameters = listOf("Landroid/content/Context;"),
                    returnType = "Z"
            )

    // Class: com.radaee.pdf.Global - The main Init that calls native activation
    val PDF_INIT_FULL =
            Fingerprint(
                    definingClass = "Lcom/radaee/pdf/Global;",
                    name = "Init",
                    parameters =
                            listOf(
                                    "Landroid/content/ContextWrapper;",
                                    "Ljava/util/ArrayList;",
                                    "I",
                                    "Ljava/lang/String;",
                                    "Ljava/lang/String;",
                                    "Ljava/lang/String;"
                            ),
                    returnType = "Z"
            )

    val PDF_IS_LICENSE_ACTIVATED =
            Fingerprint(
                    definingClass = "Lcom/radaee/pdf/Global;",
                    name = "isLicenseActivated",
                    parameters = emptyList(),
                    returnType = "Z"
            )

    val CAN_SHOW_ADS =
            Fingerprint(
                    definingClass = "Lcom/flyersoft/tools/A;",
                    name = "canShowAds",
                    parameters = emptyList(),
                    returnType = "Z"
            )

    val SET_DASH_TITLE =
            Fingerprint(
                    definingClass = "Lcom/flyersoft/moonreader/ActivityMain;",
                    name = "setDashTitle",
                    parameters = emptyList(),
                    returnType = "V"
            )
}
