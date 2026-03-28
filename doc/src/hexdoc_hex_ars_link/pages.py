from hexdoc.patchouli.page import EmptyPage


class EmptyConfigPage(EmptyPage, type="hexcasting:hex_ars_link/linker_ratio"):
    pass


class EmptyRecipePage(EmptyPage, type="hexcasting:hex_ars_link/apparatus_recipe"):
    recipe: str
    anchor: str
