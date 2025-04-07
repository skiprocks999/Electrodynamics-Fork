package voltaicapi.client.event;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.InputEvent.Key;

public abstract class AbstractKeyPressHandler {

	public abstract void handler(Key event, Minecraft minecraft);

}
