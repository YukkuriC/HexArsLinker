package io.yukkuric.hex_ars_link.hexparse

import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.hollingsworth.arsnouveau.ArsNouveau
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import io.yukkuric.hex_ars_link.iota.GlyphIota
import io.yukkuric.hexparse.parsers.str2nbt.BaseConstParser.Prefix
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

object Code2Glyph : Prefix("glyph_") {
    val KEY = GlyphIota.ID.toString()

    override fun parse(node: String): CompoundTag {
        var path = node.substring(6)
        var namespace: String
        var colonIdx = path.indexOf(":")
        if (colonIdx >= 0) {
            namespace = path.substring(0, colonIdx)
            path = path.substring(colonIdx + 1)
        } else {
            namespace = ArsNouveau.MODID
        }

        // try match glyph
        var payload: String? = null
        if (!path.startsWith("glyph_")) {
            var tester = ResourceLocation(namespace, "glyph_$path")
            if (GlyphRegistry.getSpellPart(tester) != null) payload = tester.toString()
        }
        if (payload == null) payload = ResourceLocation(namespace, path).toString()

        // avoid unstable api
        val ret = CompoundTag()
        ret.putString(HexIotaTypes.KEY_TYPE, KEY)
        ret.putString(HexIotaTypes.KEY_DATA, payload)
        return ret
    }
}