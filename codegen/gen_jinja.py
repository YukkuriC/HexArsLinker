from YukkuriC.minecraft.codegen.jinja import *

load_data_yaml('config.yaml')
batch_gen(
    load_env(__file__),
    {
        'common': 'src/main/java/io/yukkuric/hex_ars_link/config/LinkConfig.java',
        'forge': 'src/main/java/io/yukkuric/hex_ars_link/config/LinkConfigForge.java',
    },
)
