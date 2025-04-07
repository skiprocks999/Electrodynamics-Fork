package voltaicapi.prefab.screen.component.types.guitab;

import java.util.ArrayList;
import java.util.List;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.api.gas.PropertyGasTank;
import voltaicapi.api.screen.component.TextPropertySupplier;
import voltaicapi.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaicapi.prefab.screen.GenericScreen;
import voltaicapi.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaicapi.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaicapi.prefab.tile.GenericTile;
import voltaicapi.prefab.tile.components.IComponentType;
import voltaicapi.prefab.tile.components.utils.IComponentGasHandler;
import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentGasPressure extends ScreenComponentGuiTab {

	public ScreenComponentGasPressure(TextPropertySupplier infoHandler, int x, int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.PRESSURE_GAUGE, infoHandler, x, y);
	}

	public ScreenComponentGasPressure(int x, int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.PRESSURE_GAUGE, AbstractScreenComponentInfo.EMPTY, x, y);
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		if (infoHandler == EMPTY) {
			return getMaxPressureInfo();
		}
		return super.getInfo(list);
	}

	private List<? extends FormattedCharSequence> getMaxPressureInfo() {

		List<FormattedCharSequence> tooltips = new ArrayList<>();

		GenericTile generic = (GenericTile) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();

		if (generic == null) {
			return tooltips;
		}

		IComponentGasHandler handler = generic.getComponent(IComponentType.GasHandler);

		int index = 1;

		for (PropertyGasTank tank : handler.getInputTanks()) {

			tooltips.add(ModularElectricityTextUtils.tooltip("tankmaxin", index, ChatFormatter.getChatDisplayShort(tank.getMaxPressure(), DisplayUnits.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY).getVisualOrderText());

			index++;
		}

		index = 1;

		for (PropertyGasTank tank : handler.getOutputTanks()) {

			tooltips.add(ModularElectricityTextUtils.tooltip("tankmaxout", index, ChatFormatter.getChatDisplayShort(tank.getMaxPressure(), DisplayUnits.PRESSURE_ATM)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

			index++;

		}

		return tooltips;

	}

}
