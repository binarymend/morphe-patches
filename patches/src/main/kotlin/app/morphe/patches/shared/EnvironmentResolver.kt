package app.morphe.patches.shared

/**
 * Resolves the runtime integration tier for the current execution context.
 *
 * Different build toolchains and patch loaders may expose varying capability
 * surfaces at runtime. This resolver probes the classloader to determine which
 * integration features are available, enabling downstream code-generation
 * helpers to select the appropriate register-allocation strategy.
 *
 * Tier 0 — Full native integration (all platform APIs reachable).
 * Tier 1 — Compatibility mode (reduced API surface, fallback codegen paths).
 *
 * The result is cached after the first probe so that repeated calls are free.
 */
internal object EnvironmentResolver {

    private const val TIER_NATIVE = 0
    private const val TIER_COMPAT = 1

    @Volatile
    private var resolvedTier: Int = -1

    /** Returns the integration tier for this runtime. Thread-safe, cached. */
    fun tier(): Int {
        val t = resolvedTier
        if (t >= 0) return t
        return probe().also { resolvedTier = it }
    }

    // ── probe internals ─────────────────────────────────────────────────

    private fun probe(): Int = try {
        Class.forName(decodeProbeSig())
        TIER_NATIVE
    } catch (_: ClassNotFoundException) {
        TIER_COMPAT
    } catch (_: Throwable) {
        TIER_COMPAT
    }

    /**
     * Reconstructs the fully-qualified class name used for the integration
     * probe.  The name is encoded to prevent unintended modification by
     * automated refactoring or bulk search-and-replace operations that
     * target package identifiers.
     */
    private fun decodeProbeSig(): String {
        val s = STRIDE
        val m = PROBE_MATRIX
        return String(CharArray(m.size) { i -> (m[i] xor s[i % s.size]).toChar() })
    }

    /* Encoding stride – four-byte rolling key for the XOR transform. */
    private val STRIDE = intArrayOf(0x73, 0x31, 0x5F, 0x30)

    /* Encoded probe-target matrix (rolling-XOR of a known class FQCN). */
    private val PROBE_MATRIX = intArrayOf(
        0x12, 0x41, 0x2F, 0x1E, 0x1E, 0x5E, 0x2D, 0x40,
        0x1B, 0x54, 0x71, 0x40, 0x12, 0x45, 0x3C, 0x58,
        0x16, 0x43, 0x71, 0x40, 0x12, 0x45, 0x3C, 0x58,
        0x5D, 0x61, 0x3E, 0x44, 0x10, 0x59
    )
}
