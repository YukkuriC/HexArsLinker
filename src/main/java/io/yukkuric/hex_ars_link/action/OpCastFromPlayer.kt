package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster
import com.hollingsworth.arsnouveau.api.spell.Spell
import io.yukkuric.hex_ars_link.action.spell.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.server.level.ServerPlayer

object OpCastFromPlayer : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val target = args.getPlayer(0)
        val raw = args.getList(1)
        val spell = GlyphIota.grabSpell(raw)
        val caster = GlyphIota.CASTER.value
        return Triple(
            Action(spell, caster, target),
            MediaConstants.CRYSTAL_UNIT * spell.spellSize + PatternResolver.getMediaCost(ctx, spell),
            listOf(),
        )
    }

    data class Action(val spell: Spell, val caster: ISpellCaster, val owner: ServerPlayer?) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            if (owner == null) return
            caster.castSpell(ctx.world, owner, ctx.castingHand, null, spell)
        }
    }
}