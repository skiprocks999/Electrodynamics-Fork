package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.common.recipe.ModularElectricityRecipe;
import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class TimeLabelWrapperElectroRecipe extends AbstractLabelWrapper {

	public TimeLabelWrapperElectroRecipe(int xPos, int yPos) {
		super(Color.JEI_TEXT_GRAY, yPos, xPos, true);
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ChatFormatter.getChatDisplayShort(((ModularElectricityRecipe) recipe).getTicks() / 20.0, DisplayUnits.TIME_SECONDS);
	}

}
