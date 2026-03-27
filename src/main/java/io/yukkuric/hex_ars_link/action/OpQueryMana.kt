package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapShameOnYou
import com.hollingsworth.arsnouveau.api.mana.IManaCap
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry

abstract class OpQueryMana : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val env = ctx
        val player = env.caster
        val manaCap = CapabilityRegistry.getMana(player).orElse(null) ?: throw MishapShameOnYou()
        return readValue(manaCap).asActionResult
    }

    abstract fun readValue(manaCap: IManaCap): Double

    object Cur : OpQueryMana() {
        override fun readValue(manaCap: IManaCap) = manaCap.currentMana
    }

    object Max : OpQueryMana() {
        override fun readValue(manaCap: IManaCap) = manaCap.maxMana.toDouble()
    }
}