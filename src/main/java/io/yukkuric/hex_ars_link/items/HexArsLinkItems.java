package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.common.lib.HexCreativeTabs;
import com.hollingsworth.arsnouveau.setup.registry.CreativeTabRegistry;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.config.LinkConfig;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
        return new Item.Properties().stacksTo(1);
    }

    public static void register(BiConsumer<ResourceLocation, Item> regFunc) {
        for (var entry : ITEMS.entrySet()) {
            regFunc.accept(entry.getKey(), entry.getValue());
        }
    }

    public static void HookCreativeTabs(CreativeModeTab tab, Consumer<Item> regFunc) {
        if (tab.equals(HexCreativeTabs.HEX) || tab.equals(CreativeTabRegistry.BLOCKS.get())) {
            for (var item : ITEMS.values()) regFunc.accept(item);
        }
    }

    // 1.21 item stack data components
    public static class DataComps {
        public static Map<ResourceLocation, DataComponentType<?>> TYPES = new LinkedHashMap<>();
        public static <T extends DataComponentType<?>> T create(String name, T obj) {
            TYPES.put(HexArsLink.halModLoc(name), obj);
            return obj;
        }
        public static void register(BiConsumer<ResourceLocation, DataComponentType<?>> regFunc) {
            for (var entry : TYPES.entrySet()) {
                regFunc.accept(entry.getKey(), entry.getValue());
            }
        }

        public static final DataComponentType<OwnerData> LINKER_OWNER = create("owner", DataComponentType.<OwnerData>builder().persistent(OwnerData.getCODEC()).networkSynchronized(OwnerData.getSTREAM_CODEC()).build());
    }
}
