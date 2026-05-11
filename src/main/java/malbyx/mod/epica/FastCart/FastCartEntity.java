package malbyx.mod.epica.FastCart;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;

public class FastCartEntity extends Minecart {
    public FastCartEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);

        this.behavior = new FastCartBehavior(this);
    }

    private final MinecartBehavior behavior;

    @Override
    public MinecartBehavior getBehavior() {
        return this.behavior;
    }

    @Override
    public Direction getMotionDirection() {
        return this.behavior.getMotionDirection();
    }

    @Override
    public void tick() {
        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        this.checkBelowWorld();
        this.computeSpeed();
        this.handlePortal();
        this.behavior.tick();
        this.updateInWaterStateAndDoFluidPushing();
        if (this.isInLava()) {
            this.lavaIgnite();
            this.lavaHurt();
            this.fallDistance *= (double)0.5F;
        }

        this.firstTick = false;
    }

    @Override
    protected double getMaxSpeed(ServerLevel serverLevel) {
        return this.behavior.getMaxSpeed(serverLevel);
    }

    @Override
    public Vec3 getKnownMovement() {
        return this.behavior.getKnownMovement(super.getKnownMovement());
    }

    @Override
    public InterpolationHandler getInterpolation() {
        return this.behavior.getInterpolation();
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.behavior.lerpMotion(this.getDeltaMovement());
    }

    @Override
    public void lerpMotion(Vec3 vec3) {
        this.behavior.lerpMotion(vec3);
    }

    @Override
    protected void moveAlongTrack(ServerLevel serverLevel) {
        this.behavior.moveAlongTrack(serverLevel);
    }

    @Override
    protected double makeStepAlongTrack(BlockPos blockPos, RailShape railShape, double d) {
        return this.behavior.stepAlongTrack(blockPos, railShape, d);
    }

    @Override
    public void move(MoverType moverType, Vec3 vec3) {
        if (useExperimentalMovement(this.level())) {
            Vec3 vec32 = this.position().add(vec3);
            super.move(moverType, vec3);
            boolean bl = this.behavior.pushAndPickupEntities();
            if (bl) {
                super.move(moverType, vec32.subtract(this.position()));
            }

            if (moverType.equals(MoverType.PISTON)) {
                this.setOnRails(false);
            }
        } else {
            super.move(moverType, vec3);
            this.applyEffectsFromBlocks();
        }
    }

    @Override
    protected Vec3 applyNaturalSlowdown(Vec3 vec3) {
        double d = this.behavior.getSlowdownFactor();
        Vec3 vec32 = vec3.multiply(d, (double)0.0F, d);
        if (this.isInWater()) {
            vec32 = vec32.scale((double)0.95F);
        }

        return vec32;
    }
}
