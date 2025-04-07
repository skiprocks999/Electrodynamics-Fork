package voltaicapi.common.command;

import com.mojang.brigadier.CommandDispatcher;
import voltaicapi.VoltaicAPI;
import voltaicapi.api.radiation.RadiationSystem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandWipeRadiationSources {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal(VoltaicAPI.ID).requires(source -> source.hasPermission(3)).then(Commands.literal("wiperadiationsources").executes(source -> {

            RadiationSystem.wipeAllSources(source.getSource().getLevel());
            source.getSource().sendSuccess(() -> Component.literal("wiped"), true);
            return 1;

        })));


    }
}
