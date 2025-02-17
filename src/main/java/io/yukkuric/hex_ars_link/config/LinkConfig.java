package io.yukkuric.hex_ars_link.config;

public class LinkConfig {
    static API CFG;

    public static void bind(API cfg) {
        CFG = cfg;
    }

    public static double ratioLv1() {
        return CFG.ratioLv1();
    }

    public static double ratioLv2() {
        return CFG.ratioLv2();
    }

    public static double ratioLv3() {
        return CFG.ratioLv3();
    }

    public static double ratioExtraMediaCasting() {
        return CFG.ratioExtraMediaCasting();
    }

    public static double costRatePatternMana() {
        return CFG.costRatePatternMana();
    }

    public interface API {
        double ratioLv1();

        double ratioLv2();

        double ratioLv3();

        double ratioExtraMediaCasting();

        double costRatePatternMana();

        String COMMENT_RATIO_LV1 = "convert ratio for lesser linker";
        String COMMENT_RATIO_LV2 = "convert ratio for advanced linker";
        String COMMENT_RATIO_LV3 = "convert ratio for great linker";
        String COMMENT_RATIO_MEDIA_CASTING = "ADDITIONAL media cost ratio for mana used in casting patterns (total media cost = base pattern cost + <ratio> * ORIGINAL spell mana cost); 0 by default.";
        String COMMENT_MANA_CASTING_RATE = "mana discount (or penalty) rate for pattern casters (final mana cost = <ratio> * ORIGINAL spell mana cost); 1 by default.";
    }
}
