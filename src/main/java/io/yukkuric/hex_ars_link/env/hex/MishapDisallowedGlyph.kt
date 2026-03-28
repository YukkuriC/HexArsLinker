package io.yukkuric.hex_ars_link.env.hex

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapDisallowedGlyph(val glyphName: Component) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context) = dyeColor(DyeColor.BLACK)
    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {}
    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) = error("disallowed", glyphName)
}