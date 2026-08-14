package me.dumb12344.stupidmod.Mixin;

import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Container.class)
public interface ContainerMixin {
    @Overwrite
    default int getMaxStackSize() {
        return 100;
    }
}
