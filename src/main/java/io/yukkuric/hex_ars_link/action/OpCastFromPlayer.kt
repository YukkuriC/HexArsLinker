package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getPlayer
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster
import com.hollingsworth.arsnouveau.api.spell.Spell
import io.yukkuric.hex_ars_link.action.spell.PatternCaster
import io.yukkuric.hex_ars_link.action.spell.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.server.level.ServerPlayer

object OpCastFromPlayer : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getPlayer(0)
        val raw = args.getList(1)
        val spell = GlyphIota.grabSpell(raw)
        val caster = PatternCaster.buildCaster(env)
        return SpellAction.Result(
            Action(spell, caster, target),
            MediaConstants.CRYSTAL_UNIT * spell.spellSize + PatternResolver.getMediaCost(env, spell),
            listOf(),
            1 + spell.spellSize.toLong()
        )
    }

    data class Action(val spell: Spell, val caster: ISpellCaster, val owner: ServerPlayer?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (owner == null) return
            caster.castSpell(env.world, owner, env.castingHand, null, spell)
        }
    }
}