package voltaicapi.compatibility.jei.utils.ingredients;

import voltaicapi.api.gas.Gas;
import voltaicapi.api.gas.GasStack;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;

public class ModularElectricityJeiTypes {

	public static final IIngredientTypeWithSubtypes<Gas, GasStack> GAS_STACK = new IIngredientTypeWithSubtypes<>() {

		@Override
		public Class<? extends GasStack> getIngredientClass() {
			return GasStack.class;
		}

		@Override
		public Class<? extends Gas> getIngredientBaseClass() {
			return Gas.class;
		}

		@Override
		public Gas getBase(GasStack ingredient) {
			return ingredient.getGas();
		}
	};

}
