package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class LabelWrapperGeneric extends AbstractLabelWrapper {

	private final Component label;

	public LabelWrapperGeneric(Color color, int yPos, int xPos, boolean xIsEnd, Component label) {
		super(color, yPos, xPos, xIsEnd);
		this.label = label;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
		return label;
	}

}
