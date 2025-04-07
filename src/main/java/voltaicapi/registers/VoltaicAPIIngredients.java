package voltaicapi.registers;

import voltaicapi.VoltaicAPI;
import voltaicapi.common.recipe.recipeutils.CountableIngredient;
import voltaicapi.common.recipe.recipeutils.EnchantmentIngredient;
import voltaicapi.common.recipe.recipeutils.FluidIngredient;
import voltaicapi.common.recipe.recipeutils.GasIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class VoltaicAPIIngredients {
    
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, VoltaicAPI.ID);
    
    /* INGREDIENT TYPES */
    
    public static final DeferredHolder<IngredientType<?>, IngredientType<CountableIngredient>> COUNTABLE_INGREDIENT_TYPE = INGREDIENT_TYPES.register("countableingredient", () -> new IngredientType<>(CountableIngredient.CODEC));
    public static final DeferredHolder<IngredientType<?>, IngredientType<FluidIngredient>> FLUID_INGREDIENT_TYPE = INGREDIENT_TYPES.register("fluidingredient", () -> new IngredientType<>(FluidIngredient.CODEC));
    public static final DeferredHolder<IngredientType<?>, IngredientType<GasIngredient>> GAS_INGREDIENT_TYPE = INGREDIENT_TYPES.register("gasingredient", () -> new IngredientType<>(GasIngredient.CODEC));
    public static final DeferredHolder<IngredientType<?>, IngredientType<EnchantmentIngredient>> ENCHANTMENT_INGREDIENT_TYPE = INGREDIENT_TYPES.register("enchantmentingredient", () -> new IngredientType<>(EnchantmentIngredient.CODEC));

}
