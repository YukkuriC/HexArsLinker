package io.yukkuric.hex_ars_link.env.ars

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import com.hollingsworth.arsnouveau.api.spell.SpellCaster
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import com.hollingsworth.arsnouveau.client.particle.ParticleColor
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry
import io.yukkuric.hex_ars_link.iota.GlyphIota.TYPE
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PatternCaster(val env: CastingEnvironment) : SpellCaster(1) {
    init {
        color = ParticleColor.fromInt(TYPE.color())
    }

    override fun getSpellResolver(
        ctx: SpellContext,
        lvl: Level,
        plr: LivingEntity,
        hand: InteractionHand
    ): SpellResolver {
        return PatternResolver(ctx, env)
    }

    companion object {
        val CASTER_ITEM = lazy { ItemStack(ItemsRegistry.ARCHMAGE_SPELLBOOK) }
    }
}