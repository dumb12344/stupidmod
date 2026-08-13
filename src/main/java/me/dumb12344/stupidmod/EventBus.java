package me.dumb12344.stupidmod;

import me.dumb12344.stupidmod.registry.BedrockWorkstationRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.item.Items;
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

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Stupidmod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventBus {
    @SubscribeEvent
    public static void advancementkill(AdvancementEvent.AdvancementEarnEvent event){
        Entity entity = event.getEntity();
        Level level = entity.level();
        if(event.getEntity().getRandom().nextIntBetweenInclusive(1,50)==1){
            Objects.requireNonNull(level.getServer()).getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).setFrom(GameRules.BooleanValue.create(true).createRule(),level.getServer());
            for(int i=0;i<60;i++){
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightning.moveTo(entity.position());
                level.addFreshEntity(lightning);
            }
            level.explode(
                    null,
                    level.damageSources().drown(),
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
        Level level = entity.level();
        //if(level.dimension().equals(Level.NETHER)){event.setResult(Event.Result.ALLOW);}
        if (level.dimension().equals(Level.NETHER)) {
            event.setResult(Event.Result.DEFAULT);
        }
        /*if(level.getDayTime()%24000>13000) {
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
            event.getEntity().setHealth(event.getEntity().getHealth()+0.3F);
            ((Player) event.getEntity()).getFoodData().setFoodLevel(((Player) event.getEntity()).getFoodData().getFoodLevel()+1);
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
        Level level = entity.level();
        if(entity instanceof ThrownExperienceBottle){
            level.explode(entity,entity.getX(),entity.getY(),entity.getZ(),8F, Level.ExplosionInteraction.TNT);
        }
    }
    @SubscribeEvent
    public static void grindstoneOP(GrindstoneEvent.OnPlaceItem event){
        //if(!event.getBottomItem().isEmpty()){return;}
        if(
                event.getTopItem().is(Items.CRAFTING_TABLE) &&
                event.getTopItem().getCount() == 1 &&
                event.getBottomItem().is(Items.BEDROCK) &&
                event.getBottomItem().getCount() == 8
        ){
            event.setOutput(BedrockWorkstationRegistry.BEDROCK_WORKSTATION_ITEM.get().getDefaultInstance());
        }
    }

}
