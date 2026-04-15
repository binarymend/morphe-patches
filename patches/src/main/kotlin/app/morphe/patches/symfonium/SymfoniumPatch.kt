package app.morphe.patches.symfonium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants
import app.morphe.patches.symfonium.Fingerprints.FirebaseManagerFingerprint
import app.morphe.patches.symfonium.Fingerprints.IsPremiumMethodFingerprint
import app.morphe.patches.symfonium.Fingerprints.PremiumStateClassFingerprint

@Suppress("unused")
val symfoniumUnlockPatch = bytecodePatch(
    name = "Unlock Symfonium",
    description = "Unlocks the trial version of Symfonium.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY_SYMFONIUM)

    execute {
        val firebaseManagerClass = classDefBy(FirebaseManagerFingerprint.definingClass!!)
        val mutableFirebaseManager = FirebaseManagerFingerprint.match(firebaseManagerClass).classDef

        val isPremiumMethod = IsPremiumMethodFingerprint.match(mutableFirebaseManager).method

        // 1. Patch isPremium() to set the flag and return true
        isPremiumMethod.apply {
            addInstructions(0, """
                const/4 v0, 0x1
                iget-object v1, p0, Lab3;->x:Ls65;
                iput-boolean v0, v1, Ls65;->y:Z
                return v0
            """.trimIndent())
        }

        // 2. Patch e() to return 0 (hides "Beta expired" screen)
        mutableFirebaseManager.methods.firstOrNull {
            it.name == "e" && it.returnType == "I"
        }?.apply {
            addInstructions(0, "const/4 v0, 0x0\nreturn v0")
        }

        // 3. Find and patch p(long) to always set 42L
        mutableFirebaseManager.methods.firstOrNull {
            it.name == "p" && it.parameterTypes.size == 1 && it.parameterTypes[0] == "J"
        }?.apply {
            addInstructions(0, """
                const-wide/16 p1, 0x2a
                invoke-static {p1, p2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;
                move-result-object p1
                iget-object p0, p0, Lab3;->G:Lgk9;
                const/4 p2, 0x0
                invoke-virtual {p0, p2, p1}, Lgk9;->n(Ljava/lang/Object;Ljava/lang/Object;)Z
                return-void
            """.trimIndent())
        }

        // 3. Patch r(pi1) to return null (hides trial expiration text)
        mutableFirebaseManager.methods.firstOrNull {
            it.name == "r" && it.parameterTypes.size == 1 && it.parameterTypes[0] == "Lpi1;"
        }?.apply {
            addInstructions(0, """
                const/4 v0, 0x0
                return-object v0
            """.trimIndent())
        }

        // 4. Patch j(pi1) and k(pi1) to return FALSE (hides trial cards)
        mutableFirebaseManager.methods.filter {
            (it.name == "j" || it.name == "k") && it.parameterTypes.size == 1 && it.parameterTypes[0] == "Lpi1;"
        }.forEach { method ->
            method.addInstructions(0, """
                sget-object v0, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;
                return-object v0
            """.trimIndent())
        }

        // 5. Patch constructor to initialize G with 42L
        mutableFirebaseManager.methods.firstOrNull { it.name == "<init>" }?.apply {
            implementation?.apply {
                instructions.forEachIndexed { index, instruction ->
                    if (instruction.opcode.name.contains("const-wide") && instruction.toString().contains("-1")) {
                        removeInstructions(index, 1)
                        addInstructions(index, "const-wide/16 p1, 0x2a")
                    }
                }
            }
        }
    }
}
