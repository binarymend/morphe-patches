package app.morphe.patches.quickcursor

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.methodCall
import app.morphe.patcher.opcode
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.Opcode

object ProStateFingerprint : Fingerprint(
    returnType = "L",
    parameters = emptyList(),
    filters = listOf(
        methodCall(name = "getString"),
        string("yes"),
        opcode(Opcode.INVOKE_STATIC)
    )
)

