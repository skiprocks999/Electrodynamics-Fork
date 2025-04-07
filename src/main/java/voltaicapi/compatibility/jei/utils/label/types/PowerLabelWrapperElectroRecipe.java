package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.common.recipe.ModularElectricityRecipe;
import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class PowerLabelWrapperElectroRecipe extends AbstractLabelWrapper {

	private final int voltage;

	public PowerLabelWrapperElectroRecipe(int xPos, int yPos, int voltage) {
		super(Color.JEI_TEXT_GRAY, yPos, xPos, false);
		this.voltage = voltage;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ModularElectricityTextUtils.jeiTranslated("guilabel.power", voltage, ((ModularElectricityRecipe) recipe).getUsagePerTick() * 20.0 / 1000.0);
	}
}
