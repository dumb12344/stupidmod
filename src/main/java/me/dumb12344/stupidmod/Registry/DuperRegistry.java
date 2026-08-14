package me.dumb12344.stupidmod.Registry;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.Duper.DuperItem;
import me.dumb12344.stupidmod.Duper.DuperMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DuperRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Stupidmod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final RegistryObject<MenuType<DuperMenu>> DUPER_MENU = MENU_TYPES.register(
            "duper",
            ()->new MenuType<>(DuperMenu::new, FeatureFlags.VANILLA_SET)
    );
    public static final RegistryObject<Item> DUPER_ITEM = ITEMS.register("duper",()->new DuperItem(new Item.Properties()));
}
