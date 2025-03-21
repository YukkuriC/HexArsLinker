package io.yukkuric.hex_ars_link.env

import at.petrak.hexcasting.api.casting.eval.env.StaffCastEnv
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.phys.Vec3

class GlyphCallbackCastEnv(caster: ServerPlayer, val hitPos: Vec3) : StaffCastEnv(caster, InteractionHand.MAIN_HAND) {
    fun getVM(init: Iota) = CastingVM(CastingImage().copy(stack = listOf(init)), this)
    val GLYPH_RANGE = 8.0
    val GLYPH_RANGE_SQ = Math.pow(GLYPH_RANGE, 2.0) + 1e-8

    override fun isVecInRangeEnvironment(vec: Vec3) = if (vec.distanceToSqr(hitPos) <= GLYPH_RANGE_SQ) true
    else super.isVecInRangeEnvironment(vec)
}
