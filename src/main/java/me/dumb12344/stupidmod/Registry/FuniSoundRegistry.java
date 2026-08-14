package me.dumb12344.stupidmod.Registry;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FuniSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Stupidmod.MODID);
    public static final RegistryObject<SoundEvent> FUNISOUND = SOUND_EVENTS.register("sounds_damage", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Stupidmod.MODID,"sounds_damage")));
}
