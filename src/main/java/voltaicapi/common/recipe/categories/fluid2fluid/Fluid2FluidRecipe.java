package voltaicapi.common.recipe.categories.fluid2fluid;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import voltaicapi.common.recipe.recipeutils.AbstractMaterialRecipe;
import voltaicapi.common.recipe.recipeutils.FluidIngredient;
import voltaicapi.common.recipe.recipeutils.ProbableFluid;
import voltaicapi.common.recipe.recipeutils.ProbableGas;
import voltaicapi.common.recipe.recipeutils.ProbableItem;
import voltaicapi.prefab.tile.components.IComponentType;
import voltaicapi.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaicapi.prefab.tile.components.type.ComponentProcessor;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipe extends AbstractMaterialRecipe {

    private List<FluidIngredient> inputFluidIngredients;
    private FluidStack outputFluidStack;

    public Fluid2FluidRecipe(String recipeGroup, List<FluidIngredient> inputFluids, FluidStack outputFluid, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
        super(recipeGroup, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
        inputFluidIngredients = inputFluids;
        outputFluidStack = outputFluid;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
        Pair<List<Integer>, Boolean> pair = areFluidsValid(getFluidIngredients(), pr.getHolder().<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks());
        if (pair.getSecond()) {
            setFluidArrangement(pair.getFirst());
            return true;
        }
        return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
        return outputFluidStack;
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
        return inputFluidIngredients;
    }

    public interface Factory<T extends Fluid2FluidRecipe> {

        T create(String recipeGroup, List<FluidIngredient> inputFluids, FluidStack outputFluid, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts);

    }

}
