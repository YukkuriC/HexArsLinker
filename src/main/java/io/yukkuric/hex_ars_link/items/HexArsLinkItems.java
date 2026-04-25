package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.config.LinkConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public class HexArsLinkItems {
    public static Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();

    // levelled linkers
    public static final ItemLinker LINKER_BASE = bind("linker_base", new ItemLinker(LinkConfig::ratioLv1, tool()));
    public static final ItemLinker LINKER_ADVANCED = bind("linker_advanced", new ItemLinker(LinkConfig::ratioLv2, tool()));
    public static final ItemLinker LINKER_GREAT = bind("linker_great", new ItemLinker(LinkConfig::ratioLv3, tool()));

    static <T extends Item> T bind(String id, T obj) {
        ITEMS.put(HexArsLink.halModLoc(id), obj);
        return obj;
    }

    public static Item.Properties tool() {
        return new Item.Properties().tab(IXplatAbstractions.INSTANCE.getTab()).stacksTo(1);
    }

    public static void register(BiConsumer<ResourceLocation, Item> regFunc) {
        for (var entry : ITEMS.entrySet()) {
            regFunc.accept(entry.getKey(), entry.getValue());
        }
    }
}
