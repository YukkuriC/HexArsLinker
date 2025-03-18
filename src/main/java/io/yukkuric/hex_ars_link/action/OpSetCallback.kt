package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import io.yukkuric.hex_ars_link.env.CallbackStorage

object OpSetCallback : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val spell = args.getList(0)
        CallbackStorage.put(env.caster, spell)
        return listOf()
    }
}