package voltaicapi.api.electricity.formatting;

import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import net.minecraft.network.chat.Component;

public class DisplayUnits {

    public static final DisplayUnit AMPERE = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.ampere.name"), ModularElectricityTextUtils.gui("displayunit.ampere.nameplural"), ModularElectricityTextUtils.gui("displayunit.ampere.symbol"));

    public static final DisplayUnit AMP_HOUR = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.amphour.name"), ModularElectricityTextUtils.gui("displayunit.amphour.nameplural"), ModularElectricityTextUtils.gui("displayunit.amphour.symbol"));

    public static final DisplayUnit VOLTAGE = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.voltage.name"), ModularElectricityTextUtils.gui("displayunit.voltage.nameplural"), ModularElectricityTextUtils.gui("displayunit.voltage.symbol"));

    public static final DisplayUnit WATT = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.watt.name"), ModularElectricityTextUtils.gui("displayunit.watt.nameplural"), ModularElectricityTextUtils.gui("displayunit.watt.symbol"));

    public static final DisplayUnit WATT_HOUR = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.watthour.name"), ModularElectricityTextUtils.gui("displayunit.watthour.nameplural"), ModularElectricityTextUtils.gui("displayunit.watthour.symbol"));

    public static final DisplayUnit RESISTANCE = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.resistance.name"), ModularElectricityTextUtils.gui("displayunit.resistance.nameplural"), ModularElectricityTextUtils.gui("displayunit.resistance.symbol"));

    public static final DisplayUnit CONDUCTANCE = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.conductance.name"), ModularElectricityTextUtils.gui("displayunit.conductance.nameplural"), ModularElectricityTextUtils.gui("displayunit.conductance.symbol"));

    public static final DisplayUnit JOULES = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.joules.name"), ModularElectricityTextUtils.gui("displayunit.joules.nameplural"), ModularElectricityTextUtils.gui("displayunit.joules.symbol"));

    public static final DisplayUnit BUCKETS = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.buckets.name"), ModularElectricityTextUtils.gui("displayunit.buckets.nameplural"), ModularElectricityTextUtils.gui("displayunit.buckets.symbol"));

    public static final DisplayUnit TEMPERATURE_KELVIN = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.tempkelvin.name"), ModularElectricityTextUtils.gui("displayunit.tempkelvin.nameplural"), ModularElectricityTextUtils.gui("displayunit.tempkelvin.symbol"));

    public static final DisplayUnit TEMPERATURE_CELCIUS = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.tempcelcius.name"), ModularElectricityTextUtils.gui("displayunit.tempcelcius.nameplural"), ModularElectricityTextUtils.gui("displayunit.tempcelcius.symbol"));

    public static final DisplayUnit TEMPERATURE_FAHRENHEIT = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.tempfahrenheit.name"), ModularElectricityTextUtils.gui("displayunit.tempfahrenheit.nameplural"), ModularElectricityTextUtils.gui("displayunit.tempfahrenheit.symbol"));

    public static final DisplayUnit TIME_SECONDS = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.timeseconds.name"), ModularElectricityTextUtils.gui("displayunit.timeseconds.nameplural"), ModularElectricityTextUtils.gui("displayunit.timeseconds.symbol"));

    public static final DisplayUnit TIME_TICKS = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.timeticks.name"), ModularElectricityTextUtils.gui("displayunit.timeticks.nameplural"), ModularElectricityTextUtils.gui("displayunit.timeticks.symbol"));

    public static final DisplayUnit PRESSURE_ATM = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.pressureatm.name"), ModularElectricityTextUtils.gui("displayunit.pressureatm.nameplural"), ModularElectricityTextUtils.gui("displayunit.pressureatm.symbol"));

    public static final DisplayUnit PERCENTAGE = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.percentage.name"), ModularElectricityTextUtils.gui("displayunit.percentage.nameplural"), ModularElectricityTextUtils.gui("displayunit.percentage.symbol"), Component.empty());

    public static final DisplayUnit FORGE_ENERGY_UNIT = new DisplayUnit(ModularElectricityTextUtils.gui("displayunit.forgeenergyunit.name"), ModularElectricityTextUtils.gui("displayunit.forgeenergyunit.nameplural"), ModularElectricityTextUtils.gui("displayunit.forgeenergyunit.symbol"));

}
