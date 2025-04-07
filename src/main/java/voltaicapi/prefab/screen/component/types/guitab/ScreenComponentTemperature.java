package voltaicapi.prefab.screen.component.types.guitab;

import voltaicapi.api.screen.component.TextPropertySupplier;
import voltaicapi.prefab.screen.component.types.ScreenComponentSlot.IconType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentTemperature extends ScreenComponentGuiTab {

	// this could be condensed to a ScreenComponentGuiTab class, but is left for future expansion purposes
	public ScreenComponentTemperature(TextPropertySupplier infoHandler, int x, int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.TEMPERATURE, infoHandler, x, y);
	}

}