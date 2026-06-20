package malbyx.mod.epica;


import malbyx.mod.epica.SheepMilk.SheepMilkFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.Properties;

public class ModFluids {
    public static final FlowingFluid STILL_SHEEP_MILK_FLUID = (FlowingFluid) register("still_sheep_milk_fluid", new SheepMilkFluid.Source());
    public static final FlowingFluid FLOWING_SHEEP_MILK_FLUID = (FlowingFluid) register("flowing_sheep_milk_fluid", new SheepMilkFluid.Flowing());
    //public static final Block SHEEP_MILK_FLUID_BLOCK = ModBlocks.register("sheep_milk_fluid_block", (properties -> new Block(ModFluids.STILL_SHEEP_MILK_FLUID, properties)), BlockBehaviour.Properties.of(), false);
    public static final Item SHEEP_MILK_FLUID_BUCKET = ModItems.register("sheep_milk_fluid_bucket", properties -> new BucketItem(ModFluids.STILL_SHEEP_MILK_FLUID, properties), new Item.Properties());

    private static <T extends Fluid> T register(String string, T fluid) {
        return (T)(Registry.register(BuiltInRegistries.FLUID, string, fluid));
    }
}
