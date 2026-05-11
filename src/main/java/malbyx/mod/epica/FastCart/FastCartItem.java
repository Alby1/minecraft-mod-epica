package malbyx.mod.epica.FastCart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.MinecartItem;

public class FastCartItem extends MinecartItem {
    public FastCartItem(EntityType<? extends AbstractMinecart> entityType, Properties properties) {
        super(entityType, properties);
    }
}
