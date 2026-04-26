package io.yukkuric.hex_ars_link;

import at.petrak.hexcasting.common.lib.HexRegistries;
import com.hollingsworth.arsnouveau.setup.registry.APIRegistry;
import com.mojang.logging.LogUtils;
import io.yukkuric.hex_ars_link.action.HexArsActions;
import io.yukkuric.hex_ars_link.config.LinkConfigForge;
import io.yukkuric.hex_ars_link.glyph.HexCallbackSpellPart;
import io.yukkuric.hex_ars_link.iota.GlyphIota;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HexArsLink.MODID)
public class HexArsLink {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "hex_ars_link";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public HexArsLink(ModContainer container) {
        var modBus = container.getEventBus();
        // modEventBus.addListener(this::commonSetup);
        modBus.addListener((BuildCreativeModeTabContentsEvent event) -> HexArsLinkItems.HookCreativeTabs(event.getTab(), event::accept));

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);

        // hex iota interop
        modBus.addListener((RegisterEvent event) -> {
            buildRegBinder(event, Registries.ITEM, HexArsLinkItems::register);
            buildRegBinder(event, Registries.DATA_COMPONENT_TYPE, HexArsLinkItems.DataComps::register);
            buildRegBinder(event, HexRegistries.ACTION, HexArsActions::registerActions);
            buildRegBinder(event, HexRegistries.IOTA_TYPE, GlyphIota::registerSelf);
        });
        APIRegistry.registerSpell(HexCallbackSpellPart.INSTANCE);

        // cfg
        LinkConfigForge.register(container);
    }
    private static <T> void buildRegBinder(RegisterEvent e, ResourceKey<Registry<T>> key, Consumer<BiConsumer<ResourceLocation, T>> regFunc) {
        if (!key.equals(e.getRegistryKey())) return;
        regFunc.accept((id, obj) -> e.register(key, id, () -> obj));
    }

    public static MinecraftServer SERVER;

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        SERVER = event.getServer();
    }

    @SubscribeEvent
    public void onServerEnding(ServerStoppingEvent event) {
        SERVER = null;
    }

    public static ResourceLocation halModLoc(String path) {
        return ResourceLocation.tryBuild(MODID, path);
    }
}
