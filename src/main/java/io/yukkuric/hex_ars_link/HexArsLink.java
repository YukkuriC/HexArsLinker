package io.yukkuric.hex_ars_link;

import at.petrak.hexcasting.common.lib.HexRegistries;
import com.hollingsworth.arsnouveau.setup.registry.APIRegistry;
import com.mojang.logging.LogUtils;
import io.yukkuric.hex_ars_link.action.HexArsActions;
import io.yukkuric.hex_ars_link.config.LinkConfigForge;
import io.yukkuric.hex_ars_link.glyph.HexCallbackSpellPart;
import io.yukkuric.hex_ars_link.iota.GlyphIota;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HexArsLink.MODID)
public class HexArsLink {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "hex_ars_link";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public HexArsLink() {
        var context = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = context.getModEventBus();
        // modEventBus.addListener(this::commonSetup);
        HexArsLinkItems.register(modEventBus);
        modEventBus.addListener(HexArsLinkItems::HookCreativeTabs);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // hex iota interop
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((RegisterEvent event) -> {
            var key = event.getRegistryKey();
            if (key.equals(HexRegistries.ACTION)) {
                HexArsActions.registerActions();
            } else if (key.equals(HexRegistries.IOTA_TYPE)) {
                GlyphIota.registerSelf();
            }
        });
        APIRegistry.registerSpell(HexCallbackSpellPart.INSTANCE);

        // cfg
        LinkConfigForge.register(ModLoadingContext.get());
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
        return new ResourceLocation(MODID, path);
    }
}
