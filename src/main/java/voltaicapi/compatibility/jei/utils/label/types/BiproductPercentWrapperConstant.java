package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class BiproductPercentWrapperConstant extends AbstractLabelWrapper {

	private final double percentage;

	public BiproductPercentWrapperConstant(int xPos, int yPos, double percentage) {
		super(Color.JEI_TEXT_GRAY, yPos, xPos, false);
		this.percentage = percentage;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return ChatFormatter.getChatDisplayShort(percentage * 100, DisplayUnits.PERCENTAGE);
	}

}
