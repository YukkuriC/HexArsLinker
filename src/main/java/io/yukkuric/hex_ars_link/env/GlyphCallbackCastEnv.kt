package io.yukkuric.hex_ars_link.env

import at.petrak.hexcasting.api.casting.eval.env.StaffCastEnv
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand

class GlyphCallbackCastEnv(caster: ServerPlayer) : StaffCastEnv(caster, InteractionHand.MAIN_HAND) {
    fun getVM(init: Iota) = CastingVM(CastingImage().copy(stack = listOf(init)), this)
}