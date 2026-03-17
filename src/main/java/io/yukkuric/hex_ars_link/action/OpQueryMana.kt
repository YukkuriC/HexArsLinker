package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import com.hollingsworth.arsnouveau.api.mana.IManaCap
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry

abstract class OpQueryMana : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val player = env.caster ?: throw MishapBadCaster()
        val manaCap = CapabilityRegistry.getMana(player).orElse(null) ?: throw MishapBadCaster()
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