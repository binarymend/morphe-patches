package app.morphe.patches.quickcursor

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants.COMPATIBILITY_QUICK_CURSOR
import app.morphe.patches.shared.SmaliTemplates

@Suppress("unused")
val quickCursorProPatch =
        bytecodePatch(
                name = "Quick Cursor PRO",
                description = "Unlock all PRO features",
                default = true
        ) {
            compatibleWith(COMPATIBILITY_QUICK_CURSOR)

            execute {
                ProStateFingerprint.method.addInstructions(
                        0,
                        SmaliTemplates.returnStaticField(
                                ProStateFingerprint.method.returnType,
                                "lifetime"
                        )
                )
            }
        }
