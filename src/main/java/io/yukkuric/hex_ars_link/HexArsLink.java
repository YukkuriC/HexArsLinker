package io.yukkuric.hex_ars_link;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.mojang.logging.LogUtils;
import io.yukkuric.hex_ars_link.action.HexArsActions;
import io.yukkuric.hex_ars_link.config.LinkConfigForge;
import io.yukkuric.hex_ars_link.iota.GlyphIota;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.lang.reflect.Method;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HexArsLink.MODID)
public class HexArsLink {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "hex_ars_link";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final Method getterCastStat;

    static {
        try {
            getterCastStat = SpellResolver.class.getDeclaredMethod("getCastStats");
            getterCastStat.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public HexArsLink() {
        var context = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = context.getModEventBus();
        // modEventBus.addListener(this::commonSetup);
        HexArsLinkItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // hex iota interop
        modEventBus.addListener((FMLCommonSetupEvent e) -> {
            HexArsActions.registerActions();
            GlyphIota.registerSelf();
        });

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

    public static SpellStats getStatsFromResolver(SpellResolver sr) {
        try {
            return (SpellStats) getterCastStat.invoke(sr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
