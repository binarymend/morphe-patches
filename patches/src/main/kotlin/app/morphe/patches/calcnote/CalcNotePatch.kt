package app.morphe.patches.calcnote

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.calcnote.Fingerprints.TE5_B
import app.morphe.patches.calcnote.Fingerprints.TE5_D
import app.morphe.patches.calcnote.Fingerprints.TE5_E
import app.morphe.patches.calcnote.Fingerprints.TE5_F
import app.morphe.patches.calcnote.Fingerprints.TE5_INIT
import app.morphe.patches.shared.Constants

@Suppress("unused")
val calcNotePatch = bytecodePatch(
    name = "Unlock CalcNote Pro",
    description = "Unlocks all Pro features and removes ads.",
    default = true
) {
    compatibleWith(Constants.COMPATIBILITY_CALCNOTE)

    execute {
        // Force te5.f() (isPro) to always return true
        TE5_F.match(classDefBy(TE5_F.definingClass!!)).method.apply {
            if (implementation == null) return@apply
            removeInstructions(0, instructions.count())
            addInstructions(0, "const/4 v0, 0x1\nreturn v0")
        }

        // Force te5.e(CustomerInfo) (parse entitlement) to always return true
        TE5_E.match(classDefBy(TE5_E.definingClass!!)).method.apply {
            if (implementation == null) return@apply
            removeInstructions(0, instructions.count())
            addInstructions(0, "const/4 v0, 0x1\nreturn v0")
        }

        // Force te5.b() (isFree) to always return false
        TE5_B.match(classDefBy(TE5_B.definingClass!!)).method.apply {
            if (implementation == null) return@apply
            removeInstructions(0, instructions.count())
            addInstructions(0, "const/4 v0, 0x0\nreturn v0")
        }

        // Force te5.d() (isNotPro) to always return false
        TE5_D.match(classDefBy(TE5_D.definingClass!!)).method.apply {
            if (implementation == null) return@apply
            removeInstructions(0, instructions.count())
            addInstructions(0, "const/4 v0, 0x0\nreturn v0")
        }

        // Patch constructor to initialize a=3 and b=true
        TE5_INIT.match(classDefBy(TE5_INIT.definingClass!!)).method.apply {
            if (implementation == null) return@apply

            // a=3 (0x3)
            instructions.forEachIndexed { index, instruction ->
                if (instruction.opcode.name == "iput" && instruction.toString().contains("->a:I")) {
                    // Replace preceding const/4
                    removeInstructions(index - 1, 1)
                    addInstructions(index - 1, "const/4 v0, 0x3")
                }

                // b=true (0x1)
                if (instruction.opcode.name == "iput-boolean" && instruction.toString().contains("->b:Z")) {
                    // Replace preceding const/4
                    removeInstructions(index - 1, 1)
                    addInstructions(index - 1, "const/4 v0, 0x1")
                }
            }
        }
    }
}
