package me.dumb12344.stupidmod.bedrockworkstation;

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
    public boolean matches(SimpleContainer p_44002_, Level p_44003_) {
        if (p_44003_.isClientSide()) return false;
        return input.is(p_44002_.getItem(0).getItem()) && p_44002_.getItem(0).getCount() >= input.getCount();
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
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
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BedrockWorkstationRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "bedrock_workstation";
    }

    public static class Serializer implements RecipeSerializer<BedrockWorkstationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Stupidmod.MODID, "bedrock_workstation");

        @Override
        public BedrockWorkstationRecipe fromJson(ResourceLocation p_44103_, JsonObject p_44104_) {
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
        public @Nullable BedrockWorkstationRecipe fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
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
