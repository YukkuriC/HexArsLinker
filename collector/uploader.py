from YukkuriC.minecraft.uploader import *

load_cfg_changelog('config.json', 'changelog.md')

push_file = build_pusher(
    filename_contents='mod_name,game_version,mod_version',
    platform='forge',
)

push_all('.', push_file)
