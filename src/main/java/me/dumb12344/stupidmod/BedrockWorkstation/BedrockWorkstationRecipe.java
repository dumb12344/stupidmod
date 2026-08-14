package me.dumb12344.stupidmod.BedrockWorkstation;

import com.google.gson.JsonObject;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BedrockWorkstationRecipe implements Recipe<SimpleContainer> {
    private final ItemStack input;
    private final ItemStack output;
    private final ResourceLocation id;
    private final Modifiers modifiers;

    public BedrockWorkstationRecipe(ItemStack input, ItemStack output, ResourceLocation id, Modifiers modifiers) {
        this.input = input;
        this.output = output;
        this.id = id;
        this.modifiers = modifiers;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer p_44002_, Level p_44003_) {
        if (p_44003_.isClientSide()) return false;
        return input.is(p_44002_.getItem(0).getItem()) && p_44002_.getItem(0).getCount() >= input.getCount();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer p_44001_, @NotNull RegistryAccess p_267165_) {
        return output.copy();
    }

    public int getInputCount() {
        return input.getCount();
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BedrockWorkstationRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "bedrock_workstation";
    }

    public static class Serializer implements RecipeSerializer<BedrockWorkstationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Stupidmod.MODID, "bedrock_workstation");

        @Override
        public @NotNull BedrockWorkstationRecipe fromJson(@NotNull ResourceLocation p_44103_, @NotNull JsonObject p_44104_) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_44104_, "output"));
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_44104_, "input"));
            Modifiers modifiers;
            if (p_44104_.has("modifiers")) {
                modifiers = switch (p_44104_.get("modifiers").getAsString()){
                    case "armor" -> Modifiers.ARMOR;
                    case "sword" -> Modifiers.SWORD;
                    case "pickaxe" -> Modifiers.PICKAXE;
                    default -> Modifiers.NONE;
                };
            }
            else {
                modifiers = Modifiers.NONE;
            }
            return new BedrockWorkstationRecipe(input, output, p_44103_, modifiers);
        }

        @Override
        public @Nullable BedrockWorkstationRecipe fromNetwork(@NotNull ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
            ItemStack input = p_44106_.readItem();
            ItemStack output = p_44106_.readItem();
            Modifiers modifiers = p_44106_.readEnum(Modifiers.class);
            return new BedrockWorkstationRecipe(input, output, p_44105_, modifiers);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_44101_, BedrockWorkstationRecipe p_44102_) {
            p_44101_.writeItemStack(p_44102_.input, false);
            p_44101_.writeItemStack(p_44102_.output, false);
        }
    }
    public enum Modifiers {
        ARMOR,
        SWORD,
        PICKAXE,
        NONE
    }
}
