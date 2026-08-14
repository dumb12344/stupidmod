package me.dumb12344.stupidmod.Registry;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.BedrockItems.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BedrockItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final RegistryObject<Item> BEDROCK_PICKAXE = ITEMS.register("bedrock_pickaxe", ()->new BedrockPickaxeItem(new Item.Properties()));
    public static final RegistryObject<Item> BEDROCK_SWORD = ITEMS.register("bedrock_sword", ()->new BedrockSwordItem(new Item.Properties()));
    public static final RegistryObject<Item> BEDROCK_ENDER_PEARL = ITEMS.register("bedrock_ender_pearl", ()->new BedrockEnderPearlItem(new Item.Properties()));
    public static final RegistryObject<ArmorItem> BEDROCK_BOOTS = ITEMS.register("bedrock_boots",()->new BedrockBootsItem(new Item.Properties()));
    public static final RegistryObject<ArmorItem> BEDROCK_HELMET = ITEMS.register("bedrock_helmet",()->new BedrockHelmetItem(new Item.Properties()));
    public static final RegistryObject<Item> BEDROCK_ACCELERATOR = ITEMS.register("bedrock_accelerator",()->new BedrockAcceleratorItem(new Item.Properties()));
    public static final RegistryObject<Item> BEDROCK_COMMAND_LINE = ITEMS.register("bedrock_command_line",()->new BedrockCommandLineItem(new Item.Properties()));
    public static final RegistryObject<Item> BEDROCK_MAGNET = ITEMS.register("bedrock_magnet",()->new BedrockMagnet(new Item.Properties()));

}
