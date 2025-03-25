package io.yukkuric.hex_ars_link.config;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.mod.HexConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class LinkConfigForge implements LinkConfig.API {
    public ForgeConfigSpec.DoubleValue cfgRatioLv1, cfgRatioLv2, cfgRatioLv3;
    public ForgeConfigSpec.DoubleValue cfgRatioExtraMediaCasting, cfgCostRatePatternMana;
    public ForgeConfigSpec.IntValue cfgMaxCallbackRecursionDepth;

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

    @Override
    public double ratioExtraMediaCasting() {
        return cfgRatioExtraMediaCasting.get();
    }

    @Override
    public double costRatePatternMana() {
        return cfgCostRatePatternMana.get();
    }

    @Override
    public int maxCallbackRecursionDepth() {
        return cfgMaxCallbackRecursionDepth.get();
    }

    public LinkConfigForge(ForgeConfigSpec.Builder builder) {
        cfgRatioLv1 = builder.comment(COMMENT_RATIO_LV1)
                .defineInRange("ratioLv1", MediaConstants.DUST_UNIT / 1000, 0, 1e10);
        cfgRatioLv2 = builder.comment(COMMENT_RATIO_LV2)
                .defineInRange("ratioLv2", MediaConstants.DUST_UNIT / 50, 0, 1e10);
        cfgRatioLv3 = builder.comment(COMMENT_RATIO_LV3)
                .defineInRange("ratioLv3", MediaConstants.SHARD_UNIT / 100, 0, 1e10);
        cfgRatioExtraMediaCasting = builder.comment(COMMENT_RATIO_MEDIA_CASTING)
                .defineInRange("ratioExtraMediaCasting", 0, 0, 1e10);
        cfgCostRatePatternMana = builder.comment(COMMENT_MANA_CASTING_RATE)
                .defineInRange("costRatePatternMana", 1, 0, 1e10);
        cfgMaxCallbackRecursionDepth = builder.comment(COMMENT_MAX_CALLBACK_RECURSION)
                .defineInRange("maxCallbackRecursionDepth", 10, 1, 114514);
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
