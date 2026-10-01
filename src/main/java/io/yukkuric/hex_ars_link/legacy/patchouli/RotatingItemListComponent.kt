/*
 * Code in this directory is sourced from Ars Nouveau (LGPL-3.0).
 * https://github.com/baileyholl/Ars-Nouveau/blob/1.21.x/src/main/java/com/hollingsworth/arsnouveau/client/patchouli/component/RotatingItemListComponent.java
 * https://github.com/baileyholl/Ars-Nouveau/blob/1.21.x/src/main/java/com/hollingsworth/arsnouveau/client/patchouli/component/RotatingItemListComponentBase.java
 */
package io.yukkuric.hex_ars_link.legacy.patchouli

import com.google.common.collect.ImmutableList
import com.google.gson.annotations.SerializedName
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI
import com.hollingsworth.arsnouveau.api.imbuement_chamber.IImbuementRecipe
import com.hollingsworth.arsnouveau.api.registry.ImbuementRecipeRegistry
import com.hollingsworth.arsnouveau.common.crafting.recipes.EnchantingApparatusRecipe
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe
import com.hollingsworth.arsnouveau.setup.registry.RecipeRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.HolderLookup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.item.crafting.RecipeType
import vazkii.patchouli.api.ICustomComponent
import vazkii.patchouli.api.IComponentRenderContext
import vazkii.patchouli.api.IVariable
import java.util.function.UnaryOperator

/**
 * Custom Patchouli component that draws a rotating circle of items.
 * Size is 80x80. For a centered one, set X to -1.
 */
class RotatingItemListComponent : ICustomComponent {
    @SerializedName("recipe_name")
    var recipeName: String? = null

    @SerializedName("recipe_type")
    var recipeType: String? = null

    @Transient
    private var ingredients: List<Ingredient> = emptyList()

    @Transient
    private var x = 0

    @Transient
    private var y = 0

    override fun build(componentX: Int, componentY: Int, pageNum: Int) {
        x = if (componentX != -1) componentX else 17
        y = componentY
        ingredients = makeIngredients()
    }

    override fun render(ms: GuiGraphics, context: IComponentRenderContext, pticks: Float, mouseX: Int, mouseY: Int) {
        val degreePerInput = (360F / ingredients.size).toInt()
        var currentDegree = 0F
        for (input in ingredients) {
            renderIngredientAtAngle(ms, context, currentDegree, input, mouseX, mouseY)

            currentDegree += degreePerInput
        }
    }

    private fun renderIngredientAtAngle(graphics: GuiGraphics, context: IComponentRenderContext, angleIn: Float, ingredient: Ingredient, mouseX: Int, mouseY: Int) {
        if (ingredient.isEmpty) {
            return
        }

        var angle = angleIn
        angle -= 90F
        val radius = 32
        val xPos = x + Math.cos(angle * Math.PI / 180.0) * radius + 32
        val yPos = y + Math.sin(angle * Math.PI / 180.0) * radius + 32
        val ms = graphics.pose()
        ms.pushPose() // This translation makes it not stuttery. It does not affect the tooltip as that is drawn separately later.
        ms.translate(xPos - xPos.toInt(), yPos - yPos.toInt(), 0.0)
        context.renderIngredient(graphics, xPos.toInt(), yPos.toInt(), mouseX, mouseY, ingredient)
        ms.popPose()
    }

    private fun makeIngredients(): List<Ingredient> {
        val world = Minecraft.getInstance().level ?: return ArrayList()

        if (recipeType == "enchanting_apparatus") {
            val holder = world.recipeManager.getAllRecipesFor(RecipeRegistry.APPARATUS_TYPE.get()).stream()
                .filter { it.id().toString() == recipeName }.findFirst().orElse(null)
            var recipe: EnchantingApparatusRecipe? = holder?.value()
            for (type in ArsNouveauAPI.getInstance().enchantingRecipeTypes) {
                val recipe1 = world.recipeManager.getAllRecipesFor(type).stream()
                    .filter { it.id().toString() == recipeName }.findFirst().orElse(null)
                val value = recipe1?.value()
                if (value is EnchantingApparatusRecipe) {
                    recipe = value
                    break
                }
            }
            return recipe?.pedestalItems() ?: ImmutableList.of()
        } else if (recipeType == "imbuement_chamber") {
            val holder = world.recipeManager.getAllRecipesFor(RecipeRegistry.IMBUEMENT_TYPE.get()).stream()
                .filter { it.id().toString() == recipeName }.findFirst().orElse(null)
            var recipe: ImbuementRecipe? = holder?.value()
            for (type in ImbuementRecipeRegistry.INSTANCE.getRecipeTypes()) {
                val imbuementRecipeType = type as RecipeType<IImbuementRecipe>
                val recipe1 = world.recipeManager.getAllRecipesFor(imbuementRecipeType).stream()
                    .filter { it.id().toString() == recipeName }.findFirst().orElse(null)
                val value = recipe1?.value()
                if (value is ImbuementRecipe) {
                    recipe = value
                    break
                }
            }
            return recipe?.pedestalItems ?: ImmutableList.of()
        } else if (recipeType == "glyph_recipe") {
            @Suppress("UNCHECKED_CAST")
            val recipe = world.recipeManager.byKey(ResourceLocation.tryParse(recipeName)).orElse(null)
                    as RecipeHolder<out GlyphRecipe>?
            return recipe?.value()?.inputs ?: ImmutableList.of()
        } else {
            throw IllegalArgumentException("Type must be 'enchanting_apparatus', 'glyph_recipe', or 'imbuement_chamber'!")
        }
    }

    override fun onVariablesAvailable(lookup: UnaryOperator<IVariable>, registries: HolderLookup.Provider) {
        recipeName = lookup.apply(IVariable.wrap(recipeName)).asString()
        recipeType = lookup.apply(IVariable.wrap(recipeType)).asString()
    }
}
