package me.dumb12344.stupidmod.bedrockitems;

import me.dumb12344.stupidmod.registry.BedrockTagRegistry;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTier;

public class BedrockTier {
    public static final ForgeTier BEDROCK = new ForgeTier(
        5,
        -1,
        1000000F,
        0,
        15,
        BedrockTagRegistry.NEEDS_BEDROCK_TOOL,
        ()->Ingredient.of(Blocks.BEDROCK.asItem())
    );
}
