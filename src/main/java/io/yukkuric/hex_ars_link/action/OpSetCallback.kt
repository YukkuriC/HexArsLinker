package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getList
import at.petrak.hexcasting.api.spell.iota.Iota
import io.yukkuric.hex_ars_link.env.hex.CallbackStorage

object OpSetCallback : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val spell = args.getList(0)
        CallbackStorage.Put(ctx.caster, spell)
        return listOf()
    }
}