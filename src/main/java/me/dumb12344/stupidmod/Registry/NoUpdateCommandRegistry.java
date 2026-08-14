package me.dumb12344.stupidmod.Registry;

import me.dumb12344.stupidmod.NoUpdateCommand.FillNoUpdateCommand;
import me.dumb12344.stupidmod.NoUpdateCommand.SetNoUpdateCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class NoUpdateCommandRegistry {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event){
        SetNoUpdateCommand.register(event.getDispatcher(), event.getBuildContext());
        FillNoUpdateCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
