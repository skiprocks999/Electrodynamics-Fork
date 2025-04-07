package voltaicapi.api.electricity.formatting;

import net.minecraft.network.chat.Component;

public class MeasurementUnit implements IMeasurementUnit {

    private final double value;
    private final Component symbol;
    private final Component name;

    public MeasurementUnit(Component name, Component symbol, double value) {
        this.name = name;
        this.symbol = symbol;
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Component getSymbol() {
        return symbol;
    }

    @Override
    public Component getName() {
        return name;
    }
}
