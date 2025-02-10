package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster
import com.hollingsworth.arsnouveau.api.spell.Spell
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.server.level.ServerPlayer

object OpCastMyself : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val raw = args.getList(0)
        val spell = GlyphIota.grabSpell(raw)
        val caster = GlyphIota.CASTER.value
        return SpellAction.Result(
            OpCastFromPlayer.Action(spell, caster, env.caster),
            MediaConstants.DUST_UNIT * spell.spellSize,
            listOf(),
            1 + spell.spellSize.toLong()
        )
    }
}