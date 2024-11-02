package io.yukkuric.hex_ars_link;

import com.mojang.logging.LogUtils;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HexArsLink.MODID)
public class HexArsLink {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "hex_ars_link";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public HexArsLink() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // modEventBus.addListener(this::commonSetup);
        HexArsLinkItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static MinecraftServer SERVER;

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        SERVER = event.getServer();
    }
}
