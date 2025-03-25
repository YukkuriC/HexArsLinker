package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.misc.MediaConstants
import io.yukkuric.hex_ars_link.env.ars.PatternCaster
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota

object OpCastMyself : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val raw = args.getList(0)
        val spell = GlyphIota.grabSpell(raw)
        val caster = PatternCaster.buildCaster(env)
        return Triple(
            OpCastFromPlayer.Action(spell, caster, ctx.caster),
            MediaConstants.DUST_UNIT * spell.spellSize + PatternResolver.getMediaCost(ctx, spell),
            listOf(),
        )
    }
}