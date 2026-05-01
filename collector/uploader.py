from YukkuriC.minecraft.uploader import *

load_cfg_changelog()

push_file = build_pusher('mod_name,game_version,mod_version', platform='neoforge')
push_all('.', push_file)
