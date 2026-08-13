package me.dumb12344.stupidmod;

import me.dumb12344.stupidmod.C4.C4Entity;
import me.dumb12344.stupidmod.C4.C4Model;
import me.dumb12344.stupidmod.C4.C4Renderer;
import me.dumb12344.stupidmod.RocketJumper.RocketJumperRenderer;
import me.dumb12344.stupidmod.bedrockworkstation.BedrockWorkstationScreen;
import me.dumb12344.stupidmod.duper.DuperScreen;
import me.dumb12344.stupidmod.nuke.NukeRenderer;
import me.dumb12344.stupidmod.nuke.PrimedNuke;
import me.dumb12344.stupidmod.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Stupidmod.MODID)
public class Stupidmod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "stupidmod";
    // Directly reference a slf4j logger
    //private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "stupidmod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "stupidmod" namespace
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "stupidmod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Create a Deferred Register to hold SoundEvents which will all be registered under the "stupidmod" namespace
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);

    // Creates a new Block with the id "stupidmod:example_block", combining the namespace and path
    //public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "stupidmod:example_block", combining the namespace and path
    //public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "stupidmod:example_id", nutrition 1 and saturation 2
    //public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "stupidmod:example_tab" for the example item, that is placed after the combat tab
    /*public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> EXAMPLE_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
    }).build());*/

    //Funi sound
    public static final RegistryObject<SoundEvent> FUNISOUND = SOUND_EVENTS.register("sounds_damage", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID,"sounds_damage")));
    public Stupidmod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        BedrockItemRegistry.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so sounds get registered
        SOUND_EVENTS.register(modEventBus);
        NukeRegistry.BLOCKS.register(modEventBus);
        NukeRegistry.ENTITIES.register(modEventBus);
        NukeRegistry.ITEMS.register(modEventBus);
        C4Registry.ITEMS.register(modEventBus);
        C4Registry.ENTITIES.register(modEventBus);
        BedrockEntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        BedrockWorkstationRegistry.BLOCKS.register(modEventBus);
        BedrockWorkstationRegistry.ITEMS.register(modEventBus);
        BedrockWorkstationRegistry.MENU_TYPES.register(modEventBus);
        DuperRegistry.ITEMS.register(modEventBus);
        DuperRegistry.MENU_TYPES.register(modEventBus);
        RocketJumperRegistry.ENTITIES.register(modEventBus);
        RocketJumperRegistry.ITEMS.register(modEventBus);
        FallingGrassBlockRegistry.BLOCKS.register(modEventBus);
        FallingGrassBlockRegistry.ITEMS.register(modEventBus);
        ZeroFrictionGrassBlockRegistry.BLOCKS.register(modEventBus);
        ZeroFrictionGrassBlockRegistry.ITEMS.register(modEventBus);
        FakeStoneRegistry.BLOCKS.register(modEventBus);
        FakeStoneRegistry.ITEMS.register(modEventBus);
        //BedrockWorkstationRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(NukeRegistry.NUKE.get(), new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource p_123425_, ItemStack p_123426_) {
                Level level = p_123425_.getLevel();
                BlockPos blockpos = p_123425_.getPos().relative(p_123425_.getBlockState().getValue(DispenserBlock.FACING));
                PrimedNuke primedNuke = new PrimedNuke(level, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (LivingEntity)null);
                level.addFreshEntity(primedNuke);
                level.playSound(null, primedNuke.getX(), primedNuke.getY(), primedNuke.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                //p_123426_.shrink(1);
                return p_123426_;
            }
        });
        DispenserBlock.registerBehavior(C4Registry.C4_DETONATOR_ITEM.get(), new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack p_123386_) {
                blockSource.getLevel().getEntitiesOfClass(C4Entity.class,
                        AABB.ofSize(blockSource.getPos().getCenter(), 10, 10, 10)
                ).forEach(C4Entity::delayedExplode);
                return p_123386_;
            }
        });
        DispenserBlock.registerBehavior(C4Registry.C4_ITEM.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                return new C4Entity(p_123407_, p_123408_.x(), p_123408_.y(), p_123408_.z(), null);
            }
        });
        DispenserBlock.registerBehavior(C4Registry.C4_MACHINE_GUN.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                return new C4Entity(p_123407_, p_123408_.x(), p_123408_.y(), p_123408_.z(), null);
            }
            private void spawnOne(BlockSource p_123366_, ItemStack p_123367_) {
                Level level = p_123366_.getLevel();
                Position position = DispenserBlock.getDispensePosition(p_123366_);
                Direction direction = p_123366_.getBlockState().getValue(DispenserBlock.FACING);
                Projectile projectile = this.getProjectile(level, position, p_123367_);
                projectile.shoot((double)direction.getStepX(), (double)((float)direction.getStepY() + 0.1F), (double)direction.getStepZ(), this.getPower(), this.getUncertainty());
                level.addFreshEntity(projectile);
                //p_123367_.shrink(1);
            }
            public ItemStack execute(BlockSource p_123366_, ItemStack p_123367_) {
                for(int i = 0; i < 100; i++) {
                    spawnOne(p_123366_, p_123367_);
                }
                return p_123367_;
            }
        });
        DispenserBlock.registerBehavior(Items.GUNPOWDER, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack p_123386_) {
                blockSource.getLevel().getEntitiesOfClass(C4Entity.class,
                        AABB.ofSize(blockSource.getPos().getCenter(), 10, 10, 10)
                ).forEach(C4Entity::delayedDisarm);
                return p_123386_;
            }
        });
        // Some common setup code
        //LOGGER.info("HELLO FROM COMMON SETUP");
        //LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        //if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        //LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        //Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(BedrockItemRegistry.BEDROCK_ACCELERATOR);
            event.accept(BedrockItemRegistry.BEDROCK_BOOTS);
            event.accept(BedrockItemRegistry.BEDROCK_COMMAND_LINE);
            event.accept(BedrockItemRegistry.BEDROCK_ENDER_PEARL);
            event.accept(BedrockItemRegistry.BEDROCK_HELMET);
            event.accept(BedrockItemRegistry.BEDROCK_MAGNET);
            event.accept(BedrockItemRegistry.BEDROCK_PICKAXE);
            event.accept(BedrockItemRegistry.BEDROCK_SWORD);
            event.accept(BedrockWorkstationRegistry.BEDROCK_WORKSTATION_ITEM);
            event.accept(C4Registry.C4_ITEM);
            event.accept(C4Registry.C4_MACHINE_GUN);
            event.accept(C4Registry.C4_DETONATOR_ITEM);
            event.accept(DuperRegistry.DUPER_ITEM);
            event.accept(NukeRegistry.NUKE_ITEM);
            event.accept(NukeRegistry.THROWABLE_NUKE_ITEM);
            event.accept(RocketJumperRegistry.ROCKET_JUMPER);
            event.accept(FallingGrassBlockRegistry.FALLING_GRASS_ITEM);
            event.accept(ZeroFrictionGrassBlockRegistry.ZERO_FRICTION_GRASS_ITEM);
            event.accept(FakeStoneRegistry.FAKE_STONE_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(
                    () -> MenuScreens.register(BedrockWorkstationRegistry.BEDROCK_WORKSTATION_MENU.get(), BedrockWorkstationScreen::new)
            );
            event.enqueueWork(
                    () -> MenuScreens.register(DuperRegistry.DUPER_MENU.get(), DuperScreen::new)
            );
            EntityRenderers.register(BedrockEntityTypeRegistry.BEDROCK_PICKAXE_PROJECTILE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(BedrockEntityTypeRegistry.BEDROCK_SWORD_PROJECTILE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(BedrockEntityTypeRegistry.BEDROCK_ENDER_PEARL_PROJECTILE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(NukeRegistry.PRIMED_NUKE.get(), NukeRenderer::new);
            EntityRenderers.register(C4Registry.C4_ENTITY.get(), C4Renderer::new);
            EntityRenderers.register(RocketJumperRegistry.ROCKET_JUMPER_ENTITY.get(), RocketJumperRenderer::new);
            // Some client setup code
            //LOGGER.info("HELLO FROM CLIENT SETUP");
            //LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(C4Model.LAYER_LOCATION, C4Model::createBodyLayer);
        }
        @SubscribeEvent
        public static void registerColor(RegisterColorHandlersEvent.Block event) {
            event.register((p_276237_, p_276238_, p_276239_, p_276240_) ->
                p_276238_ != null && p_276239_ != null ? BiomeColors.getAverageGrassColor(p_276238_, p_276239_) : GrassColor.getDefaultColor()
            , FallingGrassBlockRegistry.FALLING_GRASS_BLOCK.get());
            event.register((p_276237_, p_276238_, p_276239_, p_276240_) ->
                p_276238_ != null && p_276239_ != null ? BiomeColors.getAverageGrassColor(p_276238_, p_276239_) : GrassColor.getDefaultColor()
            , ZeroFrictionGrassBlockRegistry.ZERO_FRICTION_GRASS_BLOCK.get());
        }
        @SubscribeEvent
        public static void registerColor(RegisterColorHandlersEvent.Item event) {
            event.register((p_92687_, p_92688_) -> {
                BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
                return event.getBlockColors().getColor(blockstate, (BlockAndTintGetter)null, (BlockPos)null, p_92688_);
            }, FallingGrassBlockRegistry.FALLING_GRASS_ITEM.get());
            event.register((p_92687_, p_92688_) -> {
                BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
                return event.getBlockColors().getColor(blockstate, (BlockAndTintGetter)null, (BlockPos)null, p_92688_);
            }, ZeroFrictionGrassBlockRegistry.ZERO_FRICTION_GRASS_ITEM.get());
        }
    }
}
