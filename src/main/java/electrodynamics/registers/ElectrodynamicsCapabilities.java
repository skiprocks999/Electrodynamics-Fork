package electrodynamics.registers;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.References;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.api.capability.types.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.capability.types.locationstorage.ILocationStorage;
import electrodynamics.api.gas.GasHandlerItemStack;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.gear.armor.types.ItemHydraulicBoots;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.gear.tools.ItemPortableCylinder;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ElectrodynamicsCapabilities {

    public static final double DEFAULT_VOLTAGE = 120.0;
    public static final String LOCATION_KEY = "location";

    public static final BlockCapability<ICapabilityElectrodynamic, @Nullable Direction> CAPABILITY_ELECTRODYNAMIC_BLOCK = BlockCapability.createSided(id("electrodynamicblock"), ICapabilityElectrodynamic.class);

    public static final ItemCapability<ILocationStorage, Void> CAPABILITY_LOCATIONSTORAGE_ITEM = ItemCapability.createVoid(id("locationstorageitem"), ILocationStorage.class);

    public static final ItemCapability<IGasHandlerItem, Void> CAPABILITY_GASHANDLER_ITEM = ItemCapability.createVoid(id("gashandleritem"), IGasHandlerItem.class);
    public static final BlockCapability<IGasHandler, @Nullable Direction> CAPABILITY_GASHANDLER_BLOCK = BlockCapability.createSided(id("gashandlerblock"), IGasHandler.class);

    public static void register(RegisterCapabilitiesEvent event) {

        /* ITEMS */

        // Electric Drill

        event.registerItem(Capabilities.ItemHandler.ITEM, (itemStack, context) -> new CapabilityItemStackHandler(ItemElectricDrill.SLOT_COUNT, itemStack)
                //
                .setOnChange((item, cap, slot) -> {
                    //
                    int fortune = 0;
                    boolean silkTouch = false;
                    double speedBoost = 1;

                    for (ItemStack content : cap.getItems()) {
                        if (!content.isEmpty() && content.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
                            for (int i = 0; i < content.getCount(); i++) {

                                switch (upgrade.subtype) {

                                case basicspeed:
                                    speedBoost = Math.min(speedBoost * 1.5, Math.pow(1.5, 3));
                                    break;
                                case advancedspeed:
                                    speedBoost = Math.min(speedBoost * 2.25, Math.pow(2.25, 3));
                                    break;
                                case fortune:
                                    if (!silkTouch) {
                                        fortune = Math.min(fortune + 1, 9);
                                    }
                                    break;
                                case silktouch:
                                    if (fortune == 0) {
                                        silkTouch = true;
                                    }
                                    break;
                                default:
                                    break;
                                }
                            }
                        }
                    }

                    CompoundTag tag = item.getOrCreateTag();
                    tag.putInt(NBTUtils.FORTUNE_ENCHANT, fortune);
                    tag.putBoolean(NBTUtils.SILK_TOUCH_ENCHANT, silkTouch);
                    tag.putDouble(NBTUtils.SPEED_ENCHANT, speedBoost);

                }), ElectrodynamicsItems.ITEM_ELECTRICDRILL.get());

        // Seismic Scanner

        event.registerItem(Capabilities.ItemHandler.ITEM, (itemStack, context) -> new CapabilityItemStackHandler(ItemSeismicScanner.SLOT_COUNT, itemStack), ElectrodynamicsItems.ITEM_SEISMICSCANNER.get());

        // Reinforced Cannister
        // TODO remember to do this for the Nuclear Science cannister as well

        event.registerItem(Capabilities.FluidHandler.ITEM, (itemStack, context) -> new RestrictedFluidHandlerItemStack.SwapEmpty(itemStack, itemStack, ItemCanister.MAX_FLUID_CAPACITY), ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get());

        // Portable Cylinder

        event.registerItem(CAPABILITY_GASHANDLER_ITEM, (itemStack, context) -> new GasHandlerItemStack(itemStack, ItemPortableCylinder.MAX_GAS_CAPCITY, ItemPortableCylinder.MAX_TEMPERATURE, ItemPortableCylinder.MAX_PRESSURE), ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get());

        // Jetpack

        event.registerItem(CAPABILITY_GASHANDLER_ITEM, (itemStack, context) -> new GasHandlerItemStack(itemStack, ItemJetpack.MAX_CAPACITY, ItemJetpack.MAX_TEMPERATURE, ItemJetpack.MAX_PRESSURE).setPredicate(ItemJetpack.getGasValidator()), ElectrodynamicsItems.ITEM_JETPACK.get());

        // Hydraulic Boots

        event.registerItem(Capabilities.FluidHandler.ITEM, (itemStack, context) -> new RestrictedFluidHandlerItemStack(itemStack, ItemHydraulicBoots.MAX_CAPACITY).setValidator(ItemHydraulicBoots.getPredicate()), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get());

        // Combat Chestplate

        event.registerItem(CAPABILITY_GASHANDLER_ITEM, (itemStack, context) -> new GasHandlerItemStack(itemStack, ItemJetpack.MAX_CAPACITY, ItemJetpack.MAX_TEMPERATURE, ItemJetpack.MAX_PRESSURE).setPredicate(ItemJetpack.getGasValidator()), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get());
        
        // Combat Boots
        
        event.registerItem(Capabilities.FluidHandler.ITEM, (itemStack, context) -> new RestrictedFluidHandlerItemStack(itemStack, ItemHydraulicBoots.MAX_CAPACITY).setValidator(ItemHydraulicBoots.getPredicate()), ElectrodynamicsItems.ITEM_COMBATBOOTS.get());

        /* TILES */

        ElectrodynamicsBlockTypes.BLOCK_ENTITY_TYPES.getEntries().forEach(entry -> {
            event.registerBlockEntity(CAPABILITY_ELECTRODYNAMIC_BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getElectrodynamicCapability(context));
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getFluidHandlerCapability(context));
            event.registerBlockEntity(CAPABILITY_GASHANDLER_BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getGasHandlerCapability(context));
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, (BlockEntityType<? extends GenericTile>) entry.get(), (tile, context) -> tile.getItemHandlerCapability(context));
        });
        
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ElectrodynamicsBlockTypes.TILE_BATTERYBOX.get(), (tile, context) -> tile.getFECapability(context));

    }

    private static final ResourceLocation id(String name) {
        return new ResourceLocation(References.ID, name);
    }
}