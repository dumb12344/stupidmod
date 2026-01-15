package me.dumb12344.stupidmod.bedrockworkstation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.dumb12344.stupidmod.Stupidmod;
import net.minecraft.core.NonNullList;
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
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack outputItem;
    private final ResourceLocation id;
    public BedrockWorkstationRecipe(NonNullList<Ingredient> inputItems, ItemStack outputItem, ResourceLocation id) {
        this.inputItems = inputItems;
        this.outputItem = outputItem;
        this.id = id;
    }
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide())return false;
        return test(container.getItem(0), inputItems.get(0));
    }
    public static boolean test(@Nullable ItemStack stack, Ingredient ingredient) {
        if (stack == null) {
            return false;
        } else if (ingredient.isEmpty()) {
            return stack.isEmpty();
        } else {
            for(ItemStack itemstack : ingredient.getItems()) {
                if (itemstack.is(stack.getItem())&&itemstack.getCount()>=stack.getCount()) {
                    return true;
                }
            }

            return false;
        }
    }
    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return outputItem.copy();
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

    public static class Type implements RecipeType<BedrockWorkstationRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "bedrock_workstation";
    }

    public static class Serializer implements RecipeSerializer<BedrockWorkstationRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Stupidmod.MODID, "bedrock_workstation");

        @Override
        public BedrockWorkstationRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new BedrockWorkstationRecipe(inputs, output, location);
        }

        @Override
        public @Nullable BedrockWorkstationRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf byteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(byteBuf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(byteBuf));
            }
            ItemStack output = byteBuf.readItem();
            return new BedrockWorkstationRecipe(inputs, output, location);
        }

        @Override
        public void toNetwork(FriendlyByteBuf byteBuf, BedrockWorkstationRecipe recipe) {
            byteBuf.writeInt(recipe.inputItems.size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.toNetwork(byteBuf);
            }
            byteBuf.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}
