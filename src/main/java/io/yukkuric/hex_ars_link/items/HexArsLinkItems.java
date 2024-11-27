package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.config.LinkConfig;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HexArsLinkItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HexArsLink.MODID);

    // levelled linkers
    public static final RegistryObject<ItemLinker> LINKER_BASE = ITEMS.register("linker_base", () -> new ItemLinker(LinkConfig::ratioLv1, tool()));
    public static final RegistryObject<ItemLinker> LINKER_ADVANCED = ITEMS.register("linker_advanced", () -> new ItemLinker(LinkConfig::ratioLv2, tool()));
    public static final RegistryObject<ItemLinker> LINKER_GREAT = ITEMS.register("linker_great", () -> new ItemLinker(LinkConfig::ratioLv3, tool()));

    public static Item.Properties tool() {
        return new Item.Properties().stacksTo(1);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
