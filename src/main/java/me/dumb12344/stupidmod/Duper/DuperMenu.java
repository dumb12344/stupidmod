package me.dumb12344.stupidmod.Duper;

import me.dumb12344.stupidmod.Registry.DuperRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DuperMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    public DuperMenu(int containerId, Inventory playerInventory){
        this(containerId,playerInventory, ContainerLevelAccess.NULL);
    }
    public DuperMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess containerLevelAccess) {
        super(DuperRegistry.DUPER_MENU.get(), containerId);
        this.access = containerLevelAccess;
        this.player = playerInventory.player;
        //inventory
        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        //container slotid(of container) x y
        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player p_38941_, int slotId) {
        long newamount = this.getSlot(slotId).getItem().getCount()*2L;
        this.getSlot(slotId).getItem().setCount(newamount>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)newamount);
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return true;
    }
}
