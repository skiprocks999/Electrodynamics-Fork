package voltaicapi.api.electricity.generator;

import voltaicapi.prefab.utilities.object.TransferPack;

public interface IElectricGenerator {

	void setMultiplier(double val);

	double getMultiplier();

	TransferPack getProduced();
}
