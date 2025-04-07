package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class TemperatureLabelWrapper extends AbstractLabelWrapper {

	public TemperatureLabelWrapper(Color color, int yPos, int xPos, boolean xIsEnd) {
		super(color, yPos, xPos, xIsEnd);
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		// TODO Auto-generated method stub
		return null;
	}

}
