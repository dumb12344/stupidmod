package me.dumb12344.stupidmod;

import me.dumb12344.stupidmod.bedrockitems.registry.BedrockItemRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Stupidmod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventBus {
    @SubscribeEvent
    public static void advancementkill(AdvancementEvent.AdvancementEarnEvent event){
        Entity entity = event.getEntity();
        if(event.getEntity().getRandom().nextIntBetweenInclusive(1,50)==1){
            entity.level().getServer().getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).setFrom(GameRules.BooleanValue.create(true).createRule(),entity.level().getServer());
            for(int i=0;i<60;i++){
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT,entity.level());
                lightning.moveTo(entity.position());
                entity.level().addFreshEntity(lightning);
            }
            entity.level().explode(
                    null,
                    entity.level().damageSources().drown(),
                    null,
                    entity.position(),
                    100.0F,
                    true,
                    Level.ExplosionInteraction.BLOCK
            );
        }
    }
    @SubscribeEvent
    public static void bedrockharvest(BlockEvent.BreakEvent event) {
        if(event.getState().getBlock().equals(Blocks.BEDROCK)&&!event.getPlayer().isCreative()){
            Block.popResource((Level)event.getLevel(),event.getPos(),Blocks.BEDROCK.asItem().getDefaultInstance());
        }
    }
    @SubscribeEvent
    public static void totem(LivingUseTotemEvent event){
        if(!event.getEntity().level().isClientSide()){
            CriteriaTriggers.USED_TOTEM.trigger((ServerPlayer)event.getEntity(), event.getTotem());
        }
        event.getEntity().level().broadcastEntityEvent(event.getEntity(), (byte)35);
        event.setCanceled(true);
    }
    @SubscribeEvent
    public static void shield(ShieldBlockEvent event){
        event.setCanceled(true);
    }
    @SubscribeEvent
    public static void breakspeed(PlayerEvent.BreakSpeed event){
        event.setNewSpeed(event.getOriginalSpeed()*60F);
    }
    @SubscribeEvent
    public static void sleep(PlayerSleepInBedEvent event) {
        Entity entity = event.getEntity();
        //if(entity.level().dimension().equals(Level.NETHER)){event.setResult(Event.Result.ALLOW);}
        if (entity.level().dimension().equals(Level.NETHER)) {
            event.setResult(Event.Result.DEFAULT);
        }
        /*if(entity.level().getDayTime()%24000>13000) {
            event.setResult(Player.BedSleepingProblem.TOO_FAR_AWAY);
        }*/
        //(event.getEntity() instanceof Player ? ((Player) event.getEntity()) : null).displayClientMessage(Component.literal(String.valueOf(event.getEntity().level().getDayTime())),false);
    }
    @SubscribeEvent
    public static void sleepTimeCheck(SleepingTimeCheckEvent event){
        //if(event.getEntity().level().getDayTime()<=13000) {
        event.setResult(Event.Result.ALLOW);
        //}
    }
    @SubscribeEvent
    public static void jump(LivingEvent.LivingJumpEvent event){
        if(event.getEntity() instanceof  Player) {
            event.getEntity().setHealth(event.getEntity().getHealth()-0.3F*-1);
            ((Player) event.getEntity()).getFoodData().setFoodLevel(((Player) event.getEntity()).getFoodData().getFoodLevel() - 1 * -1);
        }

    }
    @SubscribeEvent
    public static void wakeup(PlayerWakeUpEvent event){
        /*if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel nether = player.getServer().getLevel(Level.NETHER);
            if (nether == null) return;
            player.changeDimension(nether,new SimpleTeleporter(player.blockPosition()));
            player.teleportTo(
                    nether,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    0,0
            );
            nether.explode(
                    null,
                    player.level().damageSources().badRespawnPointExplosion(player.position()),
                    null,
                    player.position(),
                    20.0F,
                    true,
                    Level.ExplosionInteraction.BLOCK
            );
            nether.setBlock(player.blockPosition(), Blocks.LAVA.defaultBlockState(), 1);
        };*/
    }

    /*
    @SubscribeEvent
    public static void death(LivingDeathEvent event){
        if(event.getEntity() instanceof Player){
            ((Player)event.getEntity()).displayClientMessage(Component.literal("test"),false);
            event.getEntity().playSound(Stupidmod.FUNISOUND.get());
            //event.getEntity().level().playSound(((Player)event.getEntity()),event.getEntity().blockPosition(),Stupidmod.FUNISOUND.get(),SoundSource.MASTER);
        }
    }*/
    @SubscribeEvent
    public static void explodingXpBottles(ProjectileImpactEvent event){
        Projectile entity = event.getProjectile();
        if(entity instanceof ThrownExperienceBottle){
            entity.level().explode(entity,entity.getX(),entity.getY(),entity.getZ(),8F, Level.ExplosionInteraction.TNT);
        }
    }
    @SubscribeEvent
    public static void grindstoneOP(GrindstoneEvent.OnPlaceItem event){
        if(!event.getBottomItem().isEmpty()){return;}
        Enchantment[] armorEnchantmentsArray = {
                Enchantments.ALL_DAMAGE_PROTECTION,
                Enchantments.AQUA_AFFINITY,
                Enchantments.BLAST_PROTECTION,
                Enchantments.DEPTH_STRIDER,
                Enchantments.FALL_PROTECTION,
                Enchantments.PROJECTILE_PROTECTION,
                Enchantments.RESPIRATION,
                Enchantments.FIRE_PROTECTION,
                Enchantments.FROST_WALKER,
                Enchantments.THORNS,
                Enchantments.SWIFT_SNEAK
        };
        Enchantment[] commonEnchantmentsArray = {
                Enchantments.UNBREAKING,
                Enchantments.MENDING,
        };
        Enchantment[] swordEnchantmentsArray = {
                Enchantments.SHARPNESS,
                Enchantments.FIRE_ASPECT,
                Enchantments.KNOCKBACK,
                Enchantments.MOB_LOOTING,
                Enchantments.SWEEPING_EDGE,
        };
        Enchantment[] pickaxeEnchantmentsArray = {
                Enchantments.BLOCK_FORTUNE,
                Enchantments.BLOCK_EFFICIENCY,
        };
        List<Enchantment> commonEnchantments = new java.util.ArrayList<>(Arrays.stream(commonEnchantmentsArray).toList());
        List<Enchantment> armorEnchantments = new java.util.ArrayList<>(Arrays.stream(armorEnchantmentsArray).toList());
        List<Enchantment> swordEnchantments = new java.util.ArrayList<>(Arrays.stream(swordEnchantmentsArray).toList());
        List<Enchantment> pickaxeEnchantments = new java.util.ArrayList<>(Arrays.stream(pickaxeEnchantmentsArray).toList());
        armorEnchantments.addAll(commonEnchantments);
        swordEnchantments.addAll(commonEnchantments);
        pickaxeEnchantments.addAll(commonEnchantments);
        if(event.getTopItem().is(Blocks.BEDROCK.asItem())){
            ItemStack item = Items.BARRIER.getDefaultInstance();
            item.setCount(event.getTopItem().getCount());
            event.setOutput(item);
        }
        if(event.getTopItem().is(Items.WOODEN_SWORD)){
            CompoundTag test = new CompoundTag();
            ItemStack item = BedrockItemRegistry.BEDROCK_SWORD.get().getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : swordEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }
        if(event.getTopItem().is(Items.WOODEN_PICKAXE)){
            CompoundTag test = new CompoundTag();
            ItemStack item = BedrockItemRegistry.BEDROCK_PICKAXE.get().getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : pickaxeEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }
        if(event.getTopItem().is(Items.ENDER_PEARL)){
            ItemStack item = BedrockItemRegistry.BEDROCK_ENDER_PEARL.get().getDefaultInstance();
            item.setCount(event.getTopItem().getCount());
            event.setOutput(item);
        }
        if(event.getTopItem().is(Items.REDSTONE)){
            ItemStack item = BedrockItemRegistry.BEDROCK_ACCELERATOR.get().getDefaultInstance();
            event.setOutput(item);
        }
        if(event.getTopItem().is(Blocks.NETHERITE_BLOCK.asItem())){
            ItemStack item = BedrockItemRegistry.BEDROCK_COMMAND_LINE.get().getDefaultInstance();
            event.setOutput(item);
        }
        /*
        if(event.getTopItem().is(Items.WOODEN_AXE)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            ItemStack item = Items.NETHERITE_AXE.getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : enchantments)item.enchant(e, 127);
            event.setOutput(item);
        }
        if(event.getTopItem().is(Items.WOODEN_SHOVEL)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            ItemStack item = Items.NETHERITE_SHOVEL.getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : enchantments)item.enchant(e, 127);
            event.setOutput(item);
        }
        */
        if(event.getTopItem().is(Items.LEATHER_HELMET)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            ItemStack item = BedrockItemRegistry.BEDROCK_HELMET.get().getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : armorEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }if(event.getTopItem().is(Items.LEATHER_CHESTPLATE)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            ItemStack item = Items.NETHERITE_CHESTPLATE.getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : armorEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }if(event.getTopItem().is(Items.LEATHER_LEGGINGS)){
            CompoundTag test = new CompoundTag();
            test.putBoolean("Unbreakable", true);
            ItemStack item = Items.NETHERITE_LEGGINGS.getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : armorEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }if(event.getTopItem().is(Items.LEATHER_BOOTS)){
            CompoundTag test = new CompoundTag();
            ItemStack item = BedrockItemRegistry.BEDROCK_BOOTS.get().getDefaultInstance();
            item.setTag(test);
            for(Enchantment e : armorEnchantments)item.enchant(e, 127);
            event.setOutput(item);
        }
    }

}
