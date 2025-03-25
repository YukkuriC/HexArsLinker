# Hex-Ars Linker MK.2

[![Curseforge](https://badges.moddingx.org/curseforge/versions/1134295) ![CurseForge](https://badges.moddingx.org/curseforge/downloads/1134295)](https://www.curseforge.com/minecraft/mc-mods/hex-ars-linker)  
[![Modrinth](https://badges.moddingx.org/modrinth/versions/Yntfg3XG) ![Modrinth](https://badges.moddingx.org/modrinth/downloads/Yntfg3XG)](https://modrinth.com/mod/hex-ars-linker)

A Minecraft Forge 1.19.2/1.20.1 mod.

## Mana linker items
Adds a set of 3 "Mana Linker" items auto-converting the
player's [Ars Nouveau](https://github.com/baileyholl/Ars-Nouveau/tree/1.19.x) Mana
into [Hex Casting](https://github.com/FallingColors/HexMod/tree/1.19/) media. Default recipe uses Ars Nouveau's
enchanting apparatus.

## Casting Ars spell as Hex spell
Adds a pattern to read glyphs as iotas, and several patterns to cast assembled glyph list as spell in different ways

## Casting Hex spells inside Ars spell
Adds `callback` glyph and corresponding `set_callback` pattern.  
When `callback` glyph is executed at a position/an entity, the spell consumed by `set_callback` pattern will be executed with certain initial stack.

![img](src/main/resources/cover.png)

## Links:

[<img src="https://static-beta.curseforge.com/images/favicon.ico" style="width:1em"/>
CurseForge](https://www.curseforge.com/minecraft/mc-mods/hex-ars-linker)
[<img src="https://modrinth.com/favicon.ico" style="width:1em"/>Modrinth](https://modrinth.com/mod/hex-ars-linker)

## TODOs

* [x] Patchouli on both side
* [x] Configurable conversion ratio
    * [x] Dynamic Patchouli
* [ ] _(if ars on fabric)_ port to architectury & fabric/neoforge
* [x] port to newer version