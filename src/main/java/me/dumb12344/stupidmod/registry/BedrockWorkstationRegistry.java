package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.Stupidmod;
import me.dumb12344.stupidmod.bedrockworkstation.BedrockWorkstationMenu;
import me.dumb12344.stupidmod.bedrockworkstation.BedrockWorkstationBlock;
import me.dumb12344.stupidmod.bedrockworkstation.BedrockWorkstationRecipe;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BedrockWorkstationRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Stupidmod.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stupidmod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stupidmod.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Stupidmod.MODID);
    public static final RegistryObject<MenuType<BedrockWorkstationMenu>> BEDROCK_WORKSTATION_MENU = MENU_TYPES.register(
            "bedrock_workstation",
            ()->new MenuType<>(BedrockWorkstationMenu::new, FeatureFlags.VANILLA_SET)
    );
    public static final RegistryObject<BedrockWorkstationBlock> BEDROCK_WORKSTATION_BLOCK = BLOCKS.register("bedrock_workstation",() -> new BedrockWorkstationBlock(BlockBehaviour.Properties.of().strength(2.5F,1200.0F)));
    public static final RegistryObject<Item> BEDROCK_WORKSTATION_ITEM = ITEMS.register("bedrock_workstation",() -> new BlockItem(BEDROCK_WORKSTATION_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<RecipeSerializer<BedrockWorkstationRecipe>> BEDROCK_WORKSTATION_SERIALIZER = RECIPE_SERIALIZERS.register("bedrock_workstation", () -> BedrockWorkstationRecipe.Serializer.INSTANCE);
}