package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import com.hollingsworth.arsnouveau.api.item.ICasterTool
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.hollingsworth.arsnouveau.common.items.AnnotatedCodex
import com.hollingsworth.arsnouveau.common.items.Glyph
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.world.entity.decoration.ItemFrame
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

object OpReadGlyphs : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getEntity(0)
        // read all learned glyphs
        if (target is Player) return readFromPlayer(target).asActionResult

        // read from single item
        var item: ItemStack? = null
        if (target is ItemFrame) item = target.item
        else if (target is ItemEntity) item = target.item
        // TODO: what else?
        if (item != null) return readFromItem(item).asActionResult

        // nothing found
        return listOf<Iota>().asActionResult
    }

    private fun readFromPlayer(target: Player): List<GlyphIota> {
        val ret = HashSet<AbstractSpellPart>(GlyphRegistry.getDefaultStartingSpells())
        val glyphs = CapabilityRegistry.getPlayerDataCap(target).orElse(null)?.knownGlyphs ?: return listOf()
        ret.addAll(glyphs)
        return ret.map { x -> GlyphIota(x) }
    }

    private fun readFromItem(stack: ItemStack): List<GlyphIota> {
        val src = stack.item
        if (src is Glyph) return listOf(GlyphIota(src.spellPart))
        if (src is ICasterTool) return src.getSpellCaster(stack).spell.recipe.map { x -> GlyphIota(x) }
        if (src is AnnotatedCodex) return AnnotatedCodex.CodexData(stack).glyphs.map { x -> GlyphIota(x) }
        return listOf()
    }
}