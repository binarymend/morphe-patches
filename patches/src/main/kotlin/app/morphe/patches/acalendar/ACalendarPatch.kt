package app.morphe.patches.acalendar

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.acalendar.Fingerprints.isPurchased
import app.morphe.patches.shared.SmaliTemplates

@Suppress("unused")
val aCalendarUnlockPatch = bytecodePatch(
    name = "Unlock aCalendar+",
    description = "Unlocks all aCalendar+ features.",
    default = true
) {
    execute {
        val method = isPurchased.match(classDefBy(isPurchased.definingClass!!)).method
        method.removeInstructions(0, method.instructions.count())
        method.addInstructions(0, SmaliTemplates.returnBoolean(true))
    }
}
