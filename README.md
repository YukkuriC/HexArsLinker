# Hex-Ars Linker MK.2

[![Curseforge](https://badges.moddingx.org/curseforge/versions/1134295) ![CurseForge](https://badges.moddingx.org/curseforge/downloads/1134295)](https://www.curseforge.com/minecraft/mc-mods/hex-ars-linker)  
[![Modrinth](https://badges.moddingx.org/modrinth/versions/Yntfg3XG) ![Modrinth](https://badges.moddingx.org/modrinth/downloads/Yntfg3XG)](https://modrinth.com/mod/hex-ars-linker)

[<img src="https://github.com/SamsTheNerd/HexGloop/blob/73ea39b3becd/externalassets/hexdoc-badgecozy.svg?raw=true" alt="A badge for hexdoc in the style of Devins Badges" width=180>](https://yukkuric.github.io/HexArsLinker)
[<img src="https://github.com/SamsTheNerd/HexGloop/blob/73ea39b3becd/externalassets/addon-badge-cozy.svg?raw=true" alt="A badge for addons.hexxy.media in the style of Devins Badges" width=160>](https://addons.hexxy.media)

## Mana linker items
Adds a set of 3 "Mana Linker" items auto-converting the
player's [Ars Nouveau](https://github.com/baileyholl/Ars-Nouveau/tree/1.19.x) Mana
into [Hex Casting](https://github.com/FallingColors/HexMod/tree/1.19/) media. Default recipe uses Ars Nouveau's
enchanting apparatus.

## Casting Ars spell as Hex spell
Adds a pattern to read glyphs as iotas, and several patterns to cast assembled glyph list as spell in different ways
- glyphs with item tag `#hex_ars_link:glyph/disallowed`, or with `#hex_ars_link:glyph/disallowed_delegated` in `Delegated Spell` will be denied

## Casting Hex spells inside Ars spell
Adds `callback` glyph and corresponding `set_callback` pattern.  
When `callback` glyph is executed at a position/an entity, the spell consumed by `set_callback` pattern will be executed with certain initial stack.

![img](src/main/resources/cover.png)
