package voltaicapi.compatibility.jei.screenhandlers.cliableingredients;

import voltaicapi.api.gas.GasStack;
import voltaicapi.compatibility.jei.utils.ingredients.ModularElectricityJeiTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;

public class ClickableGasIngredient extends AbstractClickableIngredient<GasStack> {

    private final GasIngredientType typeIngredient;

    public ClickableGasIngredient(Rect2i rect, GasStack gasStack) {
        super(rect);
        typeIngredient = new GasIngredientType(gasStack);
    }

    @Override
    public ITypedIngredient<GasStack> getTypedIngredient() {
        return typeIngredient;
    }

    private record GasIngredientType(GasStack gasStack) implements ITypedIngredient<GasStack> {

        @Override
        public IIngredientType<GasStack> getType() {
            return ModularElectricityJeiTypes.GAS_STACK;
        }

        @Override
        public GasStack getIngredient() {
            return gasStack;
        }

    }

}
