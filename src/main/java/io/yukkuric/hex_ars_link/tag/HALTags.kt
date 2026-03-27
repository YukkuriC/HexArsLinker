package io.yukkuric.hex_ars_link.tag

import io.yukkuric.hex_ars_link.HexArsLink
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey

abstract class HALTags<T>(val registry: ResourceKey<out Registry<T>>) {
    fun create(name: String): TagKey<T> {
        return TagKey.create(registry, HexArsLink.halModLoc(name))
    }

    object Item : HALTags<net.minecraft.world.item.Item>(Registry.ITEM_REGISTRY) {
        val DISALLOWED = create("glyph/disallowed")
        val DISALLOWED_DELEGATED = create("glyph/disallowed_delegated")
    }
}