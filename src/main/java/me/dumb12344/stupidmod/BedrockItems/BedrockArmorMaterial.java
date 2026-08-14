package me.dumb12344.stupidmod.BedrockItems;

import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BedrockArmorMaterial implements ArmorMaterial {
    private final String name;
    private final SoundEvent equipSound;
    public BedrockArmorMaterial(String name, SoundEvent equipSound){
        this.name = Stupidmod.MODID+":"+name;
        this.equipSound=equipSound;
    }
    @Override
    public int getDurabilityForType(ArmorItem.@NotNull Type p_266807_) {
        return -1;
    }
    @Override
    public int getDefenseForType(ArmorItem.@NotNull Type p_267168_) {
        return 1000000;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return equipSound;
    }
    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(Blocks.BEDROCK.asItem());
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return 1000000;
    }

    @Override
    public float getKnockbackResistance() {
        return 100000F;
    }
}
