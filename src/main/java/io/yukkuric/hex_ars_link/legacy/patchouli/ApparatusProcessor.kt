/*
 * Code in this directory is sourced from Ars Nouveau (LGPL-3.0).
 * https://github.com/baileyholl/Ars-Nouveau/blob/1.21.x/src/main/java/com/hollingsworth/arsnouveau/client/patchouli/ApparatusProcessor.java
 */
package io.yukkuric.hex_ars_link.legacy.patchouli

import com.hollingsworth.arsnouveau.common.crafting.recipes.EnchantingApparatusRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import vazkii.patchouli.api.*

@Suppress("unchecked_cast")
class ApparatusProcessor : IComponentProcessor {
    var holder: RecipeHolder<out EnchantingApparatusRecipe>? = null

    override fun setup(level: Level, variables: IVariableProvider) {
        var manager = level.recipeManager
        var id = variables.get("recipe", level.registryAccess()).asString();
        holder = manager.byKey(ResourceLocation.tryParse(id)).orElse(null)
                as RecipeHolder<out EnchantingApparatusRecipe>?
    }

    override fun process(level: Level, key: String): IVariable? {
        if (holder == null) return null
        val recipe: EnchantingApparatusRecipe = holder!!.value()
        return when (key) {
            "reagent" -> recipe.reagent().items
                .map { IVariable.from(it, level.registryAccess()) }
                .let { IVariable.wrapList(it.toList(), level.registryAccess()) }

            "recipe" -> IVariable.wrap(holder!!.id().toString(), level.registryAccess())
            "output" -> IVariable.from(recipe.result(), level.registryAccess())
            "footer" -> IVariable.wrap(recipe.result().item.descriptionId, level.registryAccess())
            else -> null
        }
    }
}