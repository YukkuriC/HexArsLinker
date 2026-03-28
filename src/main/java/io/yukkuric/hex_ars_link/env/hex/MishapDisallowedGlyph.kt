package io.yukkuric.hex_ars_link.env.hex

import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapDisallowedGlyph(val glyphName: Component) : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context) = dyeColor(DyeColor.BLACK)
    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {}
    override fun errorMessage(ctx: CastingContext, errorCtx: Context) = error("disallowed", glyphName)
}