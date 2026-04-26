package io.yukkuric.hex_ars_link.hexparse

import com.hollingsworth.arsnouveau.ArsNouveau
import io.yukkuric.hex_ars_link.iota.GlyphIota
import io.yukkuric.hexparse.parsers.nbt2str.INbt2Str

object Glyph2Code : INbt2Str<GlyphIota> {
    override fun getType() = GlyphIota::class.java
    override fun parse(node: GlyphIota): String {
        val id = node.key
        val namespace = id.namespace
        var path = id.path
        if (path.startsWith("glyph_")) path = path.substring(6)
        if (namespace != ArsNouveau.MODID) path = "$namespace:$path"
        return "glyph_$path"
    }
}