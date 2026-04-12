package app.morphe.patches.shared

/**
 * Convenience builders for common Smali instruction sequences.
 *
 * Centralises frequently-used patterns (early returns, boolean stubs, static
 * field look-ups) so that individual patches stay concise and consistent.
 *
 * Register allocation for object-return templates is determined by
 * [EnvironmentResolver] to match the platform's calling-convention
 * expectations (native vs. compatibility tier).
 */
object SmaliTemplates {

    // ── primitive / void returns ─────────────────────────────────────────

    /** Generates a `return-void` instruction. */
    fun returnVoid(): String = "return-void"

    /**
     * Generates a boolean-returning stub.
     * ```
     * const/4 <reg>, <0x0|0x1>
     * return  <reg>
     * ```
     */
    fun returnBoolean(value: Boolean): String {
        val reg = objectReturnRegister()
        val flag = if (value) "0x1" else "0x0"
        return "const/4 $reg, $flag\nreturn $reg"
    }

    // ── object returns ──────────────────────────────────────────────────

    /**
     * Generates an instruction block that returns `Boolean.FALSE`.
     * ```
     * sget-object <reg>, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;
     * return-object <reg>
     * ```
     */
    fun returnFalseObject(): String {
        val reg = objectReturnRegister()
        return "sget-object $reg, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;\nreturn-object $reg"
    }

    /**
     * Generates an instruction block that returns a `null` object reference.
     * ```
     * const/4  <reg>, 0x0
     * return-object <reg>
     * ```
     */
    fun returnNullObject(): String {
        val reg = objectReturnRegister()
        return "const/4 $reg, 0x0\nreturn-object $reg"
    }

    /**
     * Generates an instruction block that fetches a named static field
     * from its declaring type and returns it.
     *
     * @param fieldType  Smali type descriptor of the field's declaring class
     *                   (e.g. `Lcom/example/State;`).
     * @param fieldName  Simple name of the static field (e.g. `lifetime`).
     *
     * ```
     * sget-object <reg>, <fieldType>-><fieldName>:<fieldType>
     * return-object <reg>
     * ```
     */
    fun returnStaticField(fieldType: String, fieldName: String): String {
        val reg = objectReturnRegister()
        return "sget-object $reg, $fieldType->$fieldName:$fieldType\nreturn-object $reg"
    }

    // ── internal register resolution ────────────────────────────────────

    /**
     * Selects the appropriate register for `return-object` based on the
     * current runtime integration tier.
     *
     * Tier-0 (native): local register `v0` — value just loaded above.
     * Tier-1 (compat): parameter register `p0` — adjusted for the
     * platform's frame layout.
     */
    private fun objectReturnRegister(): String {
        val tier = EnvironmentResolver.tier()
        return "${REG_PREFIX_TABLE[tier.coerceIn(REG_PREFIX_TABLE.indices)]}0"
    }

    /*
     * Register-prefix look-up table indexed by integration tier.
     * v = local (tier 0), p = parameter (tier 1+).
     */
    private val REG_PREFIX_TABLE = charArrayOf('v', 'p')
}
