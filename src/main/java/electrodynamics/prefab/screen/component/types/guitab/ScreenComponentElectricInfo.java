package electrodynamics.prefab.screen.component.types.guitab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentElectricInfo extends ScreenComponentGuiTab {

	private Function<ComponentElectrodynamic, Double> wattage = null;

	public ScreenComponentElectricInfo(TextPropertySupplier infoHandler, int x, int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.ENERGY_GREEN, infoHandler, x, y);
	}

	public ScreenComponentElectricInfo(int x, int y) {
		this(AbstractScreenComponentInfo.EMPTY, x, y);
	}

	public ScreenComponentElectricInfo wattage(double wattage) {
		return wattage(e -> wattage);
	}

	public ScreenComponentElectricInfo wattage(Function<ComponentElectrodynamic, Double> wattage) {
		this.wattage = wattage;
		return this;
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		if (infoHandler == EMPTY) {
			return getElectricInformation();
		}
		return super.getInfo(list);
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		if (gui instanceof GenericScreen<?> menu) {
			if (((GenericContainerBlockEntity<?>) menu.getMenu()).getUnsafeHost() instanceof GenericTile tile) {
				if (tile.getComponent(ComponentType.Electrodynamic) instanceof ComponentElectrodynamic electro) {
					if (tile instanceof IElectricGenerator generator) {
						TransferPack transfer = generator.getProduced();
						list.add(ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ElectroTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					} else {
						if (wattage == null) {
							double usage = tile.getComponent(ComponentType.Processor) instanceof ComponentProcessor proc ? proc.getUsage() * 20 : 0;
							for (ComponentProcessor proc : tile.getProcessors()) {
								if (proc != null) {
									usage += proc.getUsage() * 20;
								}
							}
							list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(usage, DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						} else {
							list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(wattage.apply(electro), DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						}
						list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					}
				}
			}
		}
		return list;
	}
}