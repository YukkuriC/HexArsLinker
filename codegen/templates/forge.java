package io.yukkuric.hex_ars_link.config;

import at.petrak.hexcasting.api.misc.MediaConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class LinkConfigForge implements LinkConfig.API {
    {%- for grp,lines in group_val(data,'type') %}
    public static ForgeConfigSpec.{{grp.capitalize()}}Value{% for line in lines %}
            Cfg_{{line.name}}{% if loop.last %};{% else %},{% endif %}
        {%- endfor %}
    {%- endfor %}{{- "\n"}}
    {%- for line in data %}
    @Override
    public {{line.type}} {{line.name}}() {
        return Cfg_{{line.name}}.get();
    }
    {%- endfor %}{{- "\n"}}

    public LinkConfigForge(ForgeConfigSpec.Builder builder) {
        {%- for line in data %}
        Cfg_{{line.name}} = builder.comment(COMMENT_{{line.name}})
                .define{{define_sub(line)}}("{{line.name}}", {{line.default}});
        {%- endfor %}
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
