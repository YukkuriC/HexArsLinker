package io.yukkuric.hex_ars_link.config;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.mod.HexConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class LinkConfigForge implements LinkConfig.API {
    public static ForgeConfigSpec.DoubleValue
            Cfg_ratioLv1,
            Cfg_ratioLv2,
            Cfg_ratioLv3,
            Cfg_ratioExtraMediaCasting,
            Cfg_costRatePatternMana;
    public static ForgeConfigSpec.IntValue
            Cfg_extraOpsConsumedForCallbacks,
            Cfg_maxCallbackRecursionDepth;
    public static ForgeConfigSpec.BooleanValue
            Cfg_useLegacyGlyphDisplay;

    @Override
    public double ratioLv1() {
        return Cfg_ratioLv1.get();
    }
    @Override
    public double ratioLv2() {
        return Cfg_ratioLv2.get();
    }
    @Override
    public double ratioLv3() {
        return Cfg_ratioLv3.get();
    }
    @Override
    public double ratioExtraMediaCasting() {
        return Cfg_ratioExtraMediaCasting.get();
    }
    @Override
    public int extraOpsConsumedForCallbacks() {
        return Cfg_extraOpsConsumedForCallbacks.get();
    }
    @Override
    public double costRatePatternMana() {
        return Cfg_costRatePatternMana.get();
    }
    @Override
    public int maxCallbackRecursionDepth() {
        return Cfg_maxCallbackRecursionDepth.get();
    }
    @Override
    public boolean useLegacyGlyphDisplay() {
        return Cfg_useLegacyGlyphDisplay.get();
    }


    public LinkConfigForge(ForgeConfigSpec.Builder builder) {
        Cfg_ratioLv1 = builder.comment(COMMENT_ratioLv1)
                .defineInRange("ratioLv1", MediaConstants.DUST_UNIT / 1000, 0, 1e10);
        Cfg_ratioLv2 = builder.comment(COMMENT_ratioLv2)
                .defineInRange("ratioLv2", MediaConstants.DUST_UNIT / 50, 0, 1e10);
        Cfg_ratioLv3 = builder.comment(COMMENT_ratioLv3)
                .defineInRange("ratioLv3", MediaConstants.SHARD_UNIT / 100, 0, 1e10);
        Cfg_ratioExtraMediaCasting = builder.comment(COMMENT_ratioExtraMediaCasting)
                .defineInRange("ratioExtraMediaCasting", 0, 0, 1e10);
        Cfg_extraOpsConsumedForCallbacks = builder.comment(COMMENT_extraOpsConsumedForCallbacks)
                .defineInRange("extraOpsConsumedForCallbacks", HexConfig.ServerConfigAccess.DEFAULT_MAX_OP_COUNT / 2, 0, Integer.MAX_VALUE);
        Cfg_costRatePatternMana = builder.comment(COMMENT_costRatePatternMana)
                .defineInRange("costRatePatternMana", 1, 0, 1e10);
        Cfg_maxCallbackRecursionDepth = builder.comment(COMMENT_maxCallbackRecursionDepth)
                .defineInRange("maxCallbackRecursionDepth", 10, 1, 114514);
        Cfg_useLegacyGlyphDisplay = builder.comment(COMMENT_useLegacyGlyphDisplay)
                .define("useLegacyGlyphDisplay", false);
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
