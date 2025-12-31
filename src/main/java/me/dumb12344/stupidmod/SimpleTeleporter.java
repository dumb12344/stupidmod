package me.dumb12344.stupidmod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class SimpleTeleporter implements ITeleporter {
    private final BlockPos targetPos;

    public SimpleTeleporter(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel dest, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        Vec3 pos = Vec3.atBottomCenterOf(targetPos);
        return new PortalInfo(pos, Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    @Override
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destinationWorld) {
        return false;
    }
}