package voltaicapi.compatibility.jei.utils.label.types;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaicapi.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaicapi.prefab.utilities.math.Color;
import net.minecraft.network.chat.Component;

public class TimeLabelWrapperConstant extends AbstractLabelWrapper {

	private final int ticks;

	public TimeLabelWrapperConstant(int xPos, int yPos, int processingTicks) {
		super(Color.JEI_TEXT_GRAY, yPos, xPos, true);
		ticks = processingTicks;
	}

	@Override
	public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {

		return ChatFormatter.getChatDisplayShort(ticks / 20.0, DisplayUnits.TIME_SECONDS);

	}

}
