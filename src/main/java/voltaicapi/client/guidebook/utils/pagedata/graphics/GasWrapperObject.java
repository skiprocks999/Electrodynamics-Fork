package voltaicapi.client.guidebook.utils.pagedata.graphics;

import voltaicapi.api.gas.Gas;
import voltaicapi.client.VoltaicAPIClientRegister;
import voltaicapi.client.guidebook.utils.components.Page;
import net.minecraft.client.gui.GuiGraphics;

public class GasWrapperObject extends AbstractGraphicWrapper<GasWrapperObject> {

	public final Gas gas;

	public GasWrapperObject(int xOffset, int yOffset, int width, int height, int trueHeight, Gas gas, GraphicTextDescriptor... descriptors) {
		super(xOffset, yOffset, xOffset, yOffset, width, height, trueHeight, descriptors);
		this.gas = gas;
	}

	@Override
	public void render(GuiGraphics graphics, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page) {

		graphics.blit(guiWidth + wrapperX + xShift, guiHeight + wrapperY, 0, width, height, VoltaicAPIClientRegister.getSprite(VoltaicAPIClientRegister.TEXTURE_GAS));

	}

}
