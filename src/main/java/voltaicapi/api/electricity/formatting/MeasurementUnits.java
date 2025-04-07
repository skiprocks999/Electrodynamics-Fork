package voltaicapi.api.electricity.formatting;

import voltaicapi.prefab.utilities.ModularElectricityTextUtils;

/**
 * An enum is simpler, however doing it this way lets us add custom measurement units in addon mods without having to update the lib mod
 */
public class MeasurementUnits {

    public static final MeasurementUnit PICO = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.pico.name"), ModularElectricityTextUtils.gui("measurementunit.pico.symbol"), 1.0E-12D);
    public static final MeasurementUnit NANO = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.nano.name"), ModularElectricityTextUtils.gui("measurementunit.nano.symbol"), 1.0E-9D);
    public static final MeasurementUnit MICRO = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.micro.name"), ModularElectricityTextUtils.gui("measurementunit.micro.symbol"), 1.0E-6D);
    public static final MeasurementUnit MILLI = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.milli.name"), ModularElectricityTextUtils.gui("measurementunit.milli.symbol"), 1.0E-3D);
    public static final MeasurementUnit NONE = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.none.name"), ModularElectricityTextUtils.gui("measurementunit.none.symbol"), 1.0);
    public static final MeasurementUnit KILO = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.kilo.name"), ModularElectricityTextUtils.gui("measurementunit.kilo.symbol"), 1.0E3D);
    public static final MeasurementUnit MEGA = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.mega.name"), ModularElectricityTextUtils.gui("measurementunit.mega.symbol"), 1.0E6D);
    public static final MeasurementUnit GIGA = new MeasurementUnit(ModularElectricityTextUtils.gui("measurementunit.giga.name"), ModularElectricityTextUtils.gui("measurementunit.giga.symbol"), 1.0E9D);

    static {
        ChatFormatter.addMeasurementUnit(PICO);
        ChatFormatter.addMeasurementUnit(NANO);
        ChatFormatter.addMeasurementUnit(MICRO);
        ChatFormatter.addMeasurementUnit(MILLI);
        ChatFormatter.addMeasurementUnit(NONE);
        ChatFormatter.addMeasurementUnit(KILO);
        ChatFormatter.addMeasurementUnit(MEGA);
        ChatFormatter.addMeasurementUnit(GIGA);
    }

}