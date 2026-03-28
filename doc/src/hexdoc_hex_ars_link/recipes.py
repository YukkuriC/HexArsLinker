# .venv\Lib\site-packages\hexdoc_hexcasting\book\recipes.py
# .venv\Lib\site-packages\hexdoc_hexcasting\_templates\recipes\hexcasting\brainsweep.html.jinja


from hexdoc.minecraft.recipe import ItemIngredientList, ItemResult, Recipe
from pydantic import Field


class ApparatusRecipe(
    Recipe,
    type="ars_nouveau:enchanting_apparatus",
    workstation="ars_nouveau:enchanting_apparatus",
):
    output: ItemResult
    pedestalItems: list[ItemIngredientList]
    reagent: ItemIngredientList
    sourceCost: int
    keepNbtOfReagent: bool = False
