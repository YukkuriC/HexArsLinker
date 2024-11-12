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

    public static interface API {
        public double ratioLv1();

        public double ratioLv2();

        public double ratioLv3();
    }
}
