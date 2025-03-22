package io.yukkuric.hex_ars_link.action.spell

import com.hollingsworth.arsnouveau.api.spell.SpellCaster
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PatternCaster(val stack: ItemStack) : SpellCaster(stack) {
    override fun getSpellResolver(
        ctx: SpellContext,
        lvl: Level,
        plr: LivingEntity,
        hand: InteractionHand
    ): SpellResolver {
        return PatternResolver(ctx)
    }
}