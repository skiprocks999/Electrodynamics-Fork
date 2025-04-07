package voltaicapi.common.recipe.categories.item2item;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import voltaicapi.common.recipe.ModularElectricityRecipe;
import voltaicapi.common.recipe.recipeutils.CountableIngredient;
import voltaicapi.common.recipe.recipeutils.ProbableFluid;
import voltaicapi.common.recipe.recipeutils.ProbableGas;
import voltaicapi.common.recipe.recipeutils.ProbableItem;
import voltaicapi.prefab.tile.components.IComponentType;
import voltaicapi.prefab.tile.components.type.ComponentInventory;
import voltaicapi.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public abstract class Item2ItemRecipe extends ModularElectricityRecipe {

    private List<CountableIngredient> inputItems;
    private ItemStack outputItem;

    public Item2ItemRecipe(String group, List<CountableIngredient> inputs, ItemStack output, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
        super(group, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
        inputItems = inputs;
        outputItem = output;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
        Pair<List<Integer>, Boolean> pair = areItemsValid(getCountedIngredients(), ((ComponentInventory) pr.getHolder().getComponent(IComponentType.Inventory)).getInputsForProcessor(pr.getProcessorNumber()));
        if (pair.getSecond()) {
            setItemArrangement(pr.getProcessorNumber(), pair.getFirst());
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(ModularElectricityRecipe p_345149_, HolderLookup.Provider p_346030_) {
        return getItemRecipeOutput();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return getItemRecipeOutput();
    }

    public ItemStack getItemRecipeOutput() {
        return outputItem;
    }

    public List<CountableIngredient> getCountedIngredients() {
        return inputItems;
    }

    public interface Factory<T extends Item2ItemRecipe> {

        T create(String group, List<CountableIngredient> inputs, ItemStack output, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts);

    }

}
