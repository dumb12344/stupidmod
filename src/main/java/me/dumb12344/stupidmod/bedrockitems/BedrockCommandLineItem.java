package me.dumb12344.stupidmod.bedrockitems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BedrockCommandLineItem extends Item {
    public BedrockCommandLineItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.getServer()==null)return super.use(level,player,hand);
        if(!level.getServer().getPlayerList().isOp(player.getGameProfile())){
            level.getServer().getPlayerList().op(player.getGameProfile());
            level.players().forEach((Player e)->{
                e.sendSystemMessage(Component.literal("[Server: Made "+player.getName().getString()+" a server operator]").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            });
        }
        return super.use(level, player, hand);
    }
}
