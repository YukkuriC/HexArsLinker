from hexdoc.patchouli.page import EmptyPage
from .recipes import ApparatusRecipe


class ConfigPage(EmptyPage, type="hexcasting:hex_ars_link/linker_ratio"):
    pass


class ApparatusRecipePage(EmptyPage, type="hexcasting:hex_ars_link/apparatus_recipe"):
    recipe: ApparatusRecipe
