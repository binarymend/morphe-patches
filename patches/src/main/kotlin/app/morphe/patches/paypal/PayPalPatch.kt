package app.morphe.patches.paypal

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.Constants
import app.morphe.patches.shared.SmaliTemplates

@Suppress("unused")
val payPalUnlimitedMoneyPatch =
        bytecodePatch(
                name = "Unlimited Money",
                description = "Adds infinite funds to your PayPal account. Do you think it will work?",
                default = true
        ) {
            compatibleWith(Constants.COMPATIBILITY_PAYPAL)

            execute {
                val balanceMethod = Fingerprints.getBalance.match(classDefBy(Fingerprints.getBalance.definingClass!!)).method
                balanceMethod.removeInstructions(0, balanceMethod.instructions.count())
                balanceMethod.addInstructions(0, "const-wide/high16 v0, 0x7ff0")
                balanceMethod.addInstructions(1, "return-wide v0")

                val isBrokeMethod = Fingerprints.isBroke.match(classDefBy(Fingerprints.isBroke.definingClass!!)).method
                isBrokeMethod.removeInstructions(0, isBrokeMethod.instructions.count())
                isBrokeMethod.addInstructions(0, SmaliTemplates.returnBoolean(false))

                val jokeMethod = Fingerprints.getInfiniteMoney.match(classDefBy(Fingerprints.getInfiniteMoney.definingClass!!)).method
                jokeMethod.addInstructions(0, "const-string v0, \"PayPal: Unlimited Money Activated! ;););)\"")
                jokeMethod.addInstructions(1, "const-string v1, \"MorphePatcher\"")
                jokeMethod.addInstructions(2, "invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I")
            }
        }

@Suppress("unused")
val payPalElonMuskModePatch =
        bytecodePatch(
                name = "Elon Musk Mode",
                description = "Renames your account to 'Elon Musk' and sets your currency to Dogecoin.",
                default = false
        ) {
            compatibleWith(Constants.COMPATIBILITY_PAYPAL)

            execute {
                // To the moon! 🚀
            }
        }