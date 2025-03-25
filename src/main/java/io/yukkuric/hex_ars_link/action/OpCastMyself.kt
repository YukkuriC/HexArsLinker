package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import io.yukkuric.hex_ars_link.env.ars.PatternCaster
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota

object OpCastMyself : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val raw = args.getList(0)
        val spell = GlyphIota.grabSpell(raw)
        val caster = PatternCaster.buildCaster(env)
        return SpellAction.Result(
            OpCastFromPlayer.Action(spell, caster, env.caster),
            MediaConstants.DUST_UNIT * spell.spellSize + PatternResolver.getMediaCost(env, spell),
            listOf(),
            1 + spell.spellSize.toLong()
        )
    }
}