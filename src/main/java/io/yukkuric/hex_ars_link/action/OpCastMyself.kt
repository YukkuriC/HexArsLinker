package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getList
import at.petrak.hexcasting.api.spell.iota.Iota
import io.yukkuric.hex_ars_link.action.spell.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota

object OpCastMyself : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val raw = args.getList(0)
        val spell = GlyphIota.grabSpell(raw)
        val caster = GlyphIota.CASTER.value
        return Triple(
            OpCastFromPlayer.Action(spell, caster, ctx.caster),
            MediaConstants.DUST_UNIT * spell.spellSize + PatternResolver.getMediaCost(ctx, spell),
            listOf(),
        )
    }
}