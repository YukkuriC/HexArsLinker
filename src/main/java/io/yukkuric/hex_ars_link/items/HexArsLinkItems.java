package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.common.lib.HexCreativeTabs;
import com.hollingsworth.arsnouveau.setup.registry.CreativeTabRegistry;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.config.LinkConfig;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

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

    public static void HookCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        var tab = event.getTab();
        if (tab.equals(HexCreativeTabs.HEX) || tab.equals(CreativeTabRegistry.BLOCKS.get())) {
            for (var item : ITEMS.getEntries())
                event.accept(item::get);
        }
    }
}
