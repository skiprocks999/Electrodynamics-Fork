package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.common.recipe.ModularElectricityRecipe;
import voltaicapi.common.recipe.recipeutils.ProbableFluid;
import voltaicapi.common.recipe.recipeutils.ProbableGas;
import voltaicapi.common.recipe.recipeutils.ProbableItem;
import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class BiproductPercentWrapperElectroRecipe extends AbstractLabelWrapper {

	private final BiproductType type;
	private final int index;

	public BiproductPercentWrapperElectroRecipe(int xPos, int yPos, BiproductType type, int index) {
		super(Color.JEI_TEXT_GRAY, yPos, xPos, false);
		this.type = type;
		this.index = index;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {

		switch(type){
			case ITEM:
				ModularElectricityRecipe electro = (ModularElectricityRecipe) recipe;
				if (electro.hasItemBiproducts() && index < electro.getItemBiproducts().size()) {
					ProbableItem item = electro.getItemBiproducts().get(index);
					return ChatFormatter.getChatDisplayShort(item.getChance() * 100, DisplayUnits.PERCENTAGE);
				}
				break;
			case FLUID:
				electro = (ModularElectricityRecipe) recipe;
				if (electro.hasFluidBiproducts() && index < electro.getFluidBiproducts().size()) {
					ProbableFluid item = electro.getFluidBiproducts().get(index);
					return ChatFormatter.getChatDisplayShort(item.getChance() * 100, DisplayUnits.PERCENTAGE);
				}
				break;
			case GAS:
				electro = (ModularElectricityRecipe) recipe;
				if (electro.hasGasBiproducts() && index < electro.getFluidBiproducts().size()) {
					ProbableGas item = electro.getGasBiproducts().get(index);
					return ChatFormatter.getChatDisplayShort(item.getChance() * 100, DisplayUnits.PERCENTAGE);
				}
				break;
		}
		return Component.empty();
	}

	public static enum BiproductType {
		ITEM, FLUID, GAS;
	}
}
