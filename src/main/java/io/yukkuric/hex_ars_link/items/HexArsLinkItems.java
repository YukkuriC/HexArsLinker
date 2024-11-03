package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.yukkuric.hex_ars_link.HexArsLink;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HexArsLinkItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HexArsLink.MODID);

    // levelled linkers
    static final Item LINKER_BASE = item("linker_base", new ItemLinker(tool()));
    static final Item LINKER_ADVANCED = item("linker_advanced", new ItemLinker(MediaConstants.DUST_UNIT, tool()));
    static final Item LINKER_GREAT = item("linker_great", new ItemLinker(MediaConstants.SHARD_UNIT, tool()));

    public static <T extends Item> T item(String name, T item) {
        ITEMS.register(name, () -> item);
        return item;
    }

    public static Item.Properties tool() {
        return new Item.Properties().tab(IXplatAbstractions.INSTANCE.getTab()).stacksTo(1);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
