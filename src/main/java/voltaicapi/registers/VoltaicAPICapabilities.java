package voltaicapi.registers;

import voltaicapi.api.radiation.util.IRadiationRecipient;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.electricity.ICapabilityElectrodynamic;
import voltaicapi.api.gas.IGasHandler;
import voltaicapi.api.gas.IGasHandlerItem;
import voltaicapi.api.misc.ILocationStorage;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class VoltaicAPICapabilities {

    public static final double DEFAULT_VOLTAGE = 120.0;
    public static final String LOCATION_KEY = "location";

    public static final BlockCapability<ICapabilityElectrodynamic, @Nullable Direction> CAPABILITY_ELECTRODYNAMIC_BLOCK = BlockCapability.createSided(VoltaicAPI.rl("electrodynamicblock"), ICapabilityElectrodynamic.class);

    public static final ItemCapability<ILocationStorage, Void> CAPABILITY_LOCATIONSTORAGE_ITEM = ItemCapability.createVoid(VoltaicAPI.rl("locationstorageitem"), ILocationStorage.class);

    public static final ItemCapability<IGasHandlerItem, Void> CAPABILITY_GASHANDLER_ITEM = ItemCapability.createVoid(VoltaicAPI.rl("gashandleritem"), IGasHandlerItem.class);
    public static final BlockCapability<IGasHandler, @Nullable Direction> CAPABILITY_GASHANDLER_BLOCK = BlockCapability.createSided(VoltaicAPI.rl("gashandlerblock"), IGasHandler.class);

    public static final EntityCapability<IRadiationRecipient, Void> CAPABILITY_RADIATIONRECIPIENT = EntityCapability.createVoid(VoltaicAPI.rl("radiationrecipient"), IRadiationRecipient.class);


}
