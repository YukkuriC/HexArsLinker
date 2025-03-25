package io.yukkuric.hex_ars_link.env.hex

import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import io.yukkuric.hex_ars_link.config.LinkConfig
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import net.minecraft.world.phys.Vec3

class GlyphCallbackCastEnv(val hitPos: Vec3, resolver: SpellResolver) {
    val recursionDepth: Int
    val hasAmbit: Boolean

    init {
        recursionDepth = getNewDepthFrom(resolver)
        hasAmbit = recursionDepth <= LinkConfig.maxCallbackRecursionDepth()
    }

    val GLYPH_RANGE = 8.0
    val GLYPH_RANGE_SQ = Math.pow(GLYPH_RANGE, 2.0) + 1e-8

    fun isVecInRangeEnvironment(vec: Vec3) =
        if (!hasAmbit) ResVecInRange.FALSE
        else if (vec.distanceToSqr(hitPos) <= GLYPH_RANGE_SQ) ResVecInRange.TRUE
        else ResVecInRange.PASS

    companion object {
        protected fun getNewDepthFrom(resolver: SpellResolver): Int {
            var res = 0
            if (resolver is PatternResolver) {
                res++
                val env = GlyphCallbackCastEnvContext.fromContext(resolver.env)
                if (env != null) res += env.recursionDepth
            }
            return res
        }
    }

    enum class ResVecInRange {
        FALSE, TRUE, PASS
    }
}
