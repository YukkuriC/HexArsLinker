package io.yukkuric.hex_ars_link.doc

import com.hollingsworth.arsnouveau.api.documentation.DocCategory
import com.hollingsworth.arsnouveau.api.documentation.ReloadDocumentationEvent.AddEntries
import com.hollingsworth.arsnouveau.api.documentation.builder.DocEntryBuilder
import com.hollingsworth.arsnouveau.api.registry.DocumentationRegistry
import com.hollingsworth.arsnouveau.setup.registry.Documentation
import io.yukkuric.hex_ars_link.HexArsLink
import io.yukkuric.hex_ars_link.items.HexArsLinkItems
import net.minecraft.network.chat.Component
import net.minecraft.world.level.ItemLike
import net.neoforged.bus.api.SubscribeEvent


object HALDocuments : Documentation() {
    @SubscribeEvent
    fun addPages(event: AddEntries) {
        addPage(
            MyBuilder(DocumentationRegistry.ITEMS, "linkers")
                .withIcon(HexArsLinkItems.LINKER_ADVANCED.value)
                .withTitle(Component.translatable("item.hex_ars_link.linkers"))
                .withTextPage("hex_ars_link.page.linkers.0.ars")
                .withCraftingPages("linker_lv1", HexArsLinkItems.LINKER_BASE.value)
                .withCraftingPages("linker_lv2", HexArsLinkItems.LINKER_ADVANCED.value)
                .withCraftingPages("linker_lv3", HexArsLinkItems.LINKER_GREAT.value)
        )
    }

    class MyBuilder(cat: DocCategory, name: String) : DocEntryBuilder(cat, name, HexArsLink.halModLoc(name)) {
        override fun withCraftingPages(id: String, item: ItemLike) =
            super.withCraftingPages(HexArsLink.halModLoc(id), item)
    }
}