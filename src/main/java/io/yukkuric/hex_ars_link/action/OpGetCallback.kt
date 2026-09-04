package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.NullIota
import io.yukkuric.hex_ars_link.env.hex.CallbackStorage
import io.yukkuric.yclib_kt.legacy.caster

object OpGetCallback : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val spell = CallbackStorage.Get(env.caster) ?: return listOf(NullIota())
        return listOf(ListIota(spell))
    }
}