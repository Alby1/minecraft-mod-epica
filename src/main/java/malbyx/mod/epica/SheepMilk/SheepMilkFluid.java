package malbyx.mod.epica.SheepMilk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SheepMilkFluid extends FlowingFluid {

    @Override
    public Fluid getFlowing() {
        return null;
    }

    @Override
    public Fluid getSource() {
        return null;
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverLevel) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return 0;
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return 0;
    }

    @Override
    public Item getBucket() {
        return null;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return null;
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return false;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 0;
    }

    public static class Source extends SheepMilkFluid {
        public Source() {
        }

        public int getAmount(FluidState fluidState) {
            return 8;
        }

        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends SheepMilkFluid {
        public Flowing() {
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getAmount(FluidState fluidState) {
            return (Integer)fluidState.getValue(LEVEL);
        }

        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }
}
