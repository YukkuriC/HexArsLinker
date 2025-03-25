package io.yukkuric.hex_ars_link.env.hex

import at.petrak.hexcasting.api.casting.eval.env.StaffCastEnv
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.iota.Iota
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import io.yukkuric.hex_ars_link.config.LinkConfig
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.phys.Vec3

class GlyphCallbackCastEnv(caster: ServerPlayer, val hitPos: Vec3, resolver: SpellResolver) :
    StaffCastEnv(caster, InteractionHand.MAIN_HAND) {
    val recursionDepth: Int
    val hasAmbit: Boolean

    init {
        recursionDepth = getNewDepthFrom(resolver)
        hasAmbit = recursionDepth <= LinkConfig.maxCallbackRecursionDepth()
    }

    fun getVM(init: Iota) = CastingVM(
        CastingImage().copy(
            stack = listOf(init),
            opsConsumed = LinkConfig.extraOpsConsumedForCallbacks().toLong()
        ), this
    )

    val GLYPH_RANGE = 8.0
    val GLYPH_RANGE_SQ = Math.pow(GLYPH_RANGE, 2.0) + 1e-8

    override fun isVecInRangeEnvironment(vec: Vec3) =
        if (!hasAmbit) false
        else if (vec.distanceToSqr(hitPos) <= GLYPH_RANGE_SQ) true
        else super.isVecInRangeEnvironment(vec)

    companion object {
        protected fun getNewDepthFrom(resolver: SpellResolver): Int {
            var res = 0
            if (resolver is PatternResolver) {
                res++
                if (resolver.env is GlyphCallbackCastEnv) res += resolver.env.recursionDepth
            }
            return res
        }
    }
}
