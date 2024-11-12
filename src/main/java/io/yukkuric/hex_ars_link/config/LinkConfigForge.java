package io.yukkuric.hex_ars_link.config;

import at.petrak.hexcasting.api.misc.MediaConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class LinkConfigForge implements LinkConfig.API {
    public ForgeConfigSpec.DoubleValue cfgRatioLv1, cfgRatioLv2, cfgRatioLv3;

    @Override
    public double ratioLv1() {
        return cfgRatioLv1.get();
    }

    @Override
    public double ratioLv2() {
        return cfgRatioLv2.get();
    }

    @Override
    public double ratioLv3() {
        return cfgRatioLv3.get();
    }

    public LinkConfigForge(ForgeConfigSpec.Builder builder) {
        cfgRatioLv1 = builder.comment("convert ratio for lesser linker")
                .defineInRange("ratioLv1", MediaConstants.DUST_UNIT / 1000, 0, 1e10);
        cfgRatioLv2 = builder.comment("convert ratio for advanced linker")
                .defineInRange("ratioLv2", MediaConstants.DUST_UNIT / 50, 0, 1e10);
        cfgRatioLv3 = builder.comment("convert ratio for great linker")
                .defineInRange("ratioLv3", MediaConstants.SHARD_UNIT / 100, 0, 1e10);
    }

    private static final Pair<LinkConfigForge, ForgeConfigSpec> CFG_REGISTRY;

    static {
        CFG_REGISTRY = new ForgeConfigSpec.Builder().configure(LinkConfigForge::new);
    }

    public static void register(ModLoadingContext ctx) {
        LinkConfig.bind(CFG_REGISTRY.getKey());
        ctx.registerConfig(ModConfig.Type.COMMON, CFG_REGISTRY.getValue());
    }
}
