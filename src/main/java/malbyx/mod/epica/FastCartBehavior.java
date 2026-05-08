package malbyx.mod.epica;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;

public class FastCartBehavior extends OldMinecartBehavior {
    public FastCartBehavior(AbstractMinecart abstractMinecart) {
        super(abstractMinecart);
    }

    @Override
    public double getMaxSpeed(ServerLevel serverLevel) {
        return this.minecart.isInWater() ? 0.2 : 1;
    }
}
