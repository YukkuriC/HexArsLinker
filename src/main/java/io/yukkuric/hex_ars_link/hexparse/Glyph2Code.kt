package io.yukkuric.hex_ars_link.hexparse

import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.hollingsworth.arsnouveau.ArsNouveau
import io.yukkuric.hex_ars_link.hexparse.Code2Glyph.KEY
import io.yukkuric.hexparse.parsers.nbt2str.INbt2Str
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

object Glyph2Code : INbt2Str {
    override fun match(node: CompoundTag) = isType(node, KEY)
    override fun parse(node: CompoundTag): String {
        val id = ResourceLocation(node.getString(HexIotaTypes.KEY_DATA))
        val namespace = id.namespace
        var path = id.path
        if (path.startsWith("glyph_")) path = path.substring(6)
        if (namespace != ArsNouveau.MODID) path = "$namespace:$path"
        return "glyph_$path"
    }
}