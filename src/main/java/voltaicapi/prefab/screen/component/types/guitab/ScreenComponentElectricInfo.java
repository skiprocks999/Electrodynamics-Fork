package voltaicapi.prefab.screen.component.types.guitab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import voltaicapi.api.electricity.formatting.ChatFormatter;
import voltaicapi.api.electricity.formatting.DisplayUnits;
import voltaicapi.api.electricity.generator.IElectricGenerator;
import voltaicapi.api.screen.component.TextPropertySupplier;
import voltaicapi.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaicapi.prefab.screen.GenericScreen;
import voltaicapi.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaicapi.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaicapi.prefab.tile.GenericTile;
import voltaicapi.prefab.tile.components.IComponentType;
import voltaicapi.prefab.tile.components.type.ComponentElectrodynamic;
import voltaicapi.prefab.tile.components.type.ComponentProcessor;
import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import voltaicapi.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
				if (tile.getComponent(IComponentType.Electrodynamic) instanceof ComponentElectrodynamic electro) {
					if (tile instanceof IElectricGenerator generator) {
						TransferPack transfer = generator.getProduced();
						list.add(ModularElectricityTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnits.AMPERE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ModularElectricityTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnits.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ModularElectricityTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					} else {
						double satisfaction = 0;
						if (wattage == null) {
							double perTick = tile.getComponent(IComponentType.Processor) instanceof ComponentProcessor proc ? proc.getUsage() : 0;
							for (ComponentProcessor proc : tile.getProcessors()) {
								if (proc != null) {
									perTick += proc.getUsage();
								}
							}
							list.add(ModularElectricityTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(perTick * 20, DisplayUnits.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
							if(perTick == 0) {
								satisfaction = 1;
							} else if(electro.getJoulesStored() > 0) {
								satisfaction = electro.getJoulesStored() >= perTick ? 1 : electro.getJoulesStored() / perTick;
							}
						} else {
							double watts = wattage.apply(electro);
							double perTick = watts / 20.0;

							if(perTick == 0) {
								satisfaction = 1;
							} else if(electro.getJoulesStored() > 0) {
								satisfaction = electro.getJoulesStored() >= perTick ? 1 : electro.getJoulesStored() / perTick;
							}

							list.add(ModularElectricityTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(watts, DisplayUnits.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						}
						list.add(ModularElectricityTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ModularElectricityTextUtils.gui("machine.satisfaction", ChatFormatter.getChatDisplayShort(satisfaction * 100.0, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					}
				}
			}
		}
		return list;
	}
}