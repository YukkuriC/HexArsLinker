from YukkuriC.minecraft.collector import *
import os

do_collect_simple(os.path.dirname(__file__))

with open('CHANGELOG.md', encoding='utf-8') as f:
    print(f.read())