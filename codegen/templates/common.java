package io.yukkuric.hex_ars_link.config;

public class LinkConfig {
    static API CFG;

    public static void bind(API cfg) {
        CFG = cfg;
    }

    {%- for line in data %}
    public static {{line.type}} {{line.name}}() {
        return CFG.{{line.name}}();
    }
    {%- endfor %}

    public interface API {
        {%- for line in data %}
        {{line.type}} {{line.name}}();
        {%- endfor %}

        {%- for line in data %}
        String COMMENT_{{line.name}} = "{{line.descrip}}";
        {%- endfor %}
    }
}
