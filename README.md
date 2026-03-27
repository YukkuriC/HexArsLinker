# Hex-Ars Linker MK.2

A Minecraft Forge 1.19.2/1.20.1 mod.
[Online HexBook](https://yukkuric.github.io/HexArsLinker)

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
