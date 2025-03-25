package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.ListIota
import io.yukkuric.hex_ars_link.env.hex.CallbackStorage

object OpGetCallback : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val spell = CallbackStorage.Get(ctx.caster)
        return listOf(ListIota(spell))
    }
}