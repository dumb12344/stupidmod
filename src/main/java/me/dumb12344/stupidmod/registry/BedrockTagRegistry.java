package me.dumb12344.stupidmod.registry;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BedrockTagRegistry {
    public static final TagKey<Block> NEEDS_BEDROCK_TOOL = tag("needs_bedrock_tool");
    private static TagKey<Block> tag(String name){return BlockTags.create(new ResourceLocation(Stupidmod.MODID, name));}
}
