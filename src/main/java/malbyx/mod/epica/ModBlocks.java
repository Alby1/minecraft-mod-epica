package malbyx.mod.epica;

import malbyx.mod.epica.CraftingCraft.CraftingCraftBlock;
import malbyx.mod.epica.GoldCup.GoldCupBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraft.world.item.Items.registerItem;

public class ModBlocks {
    public static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }


    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, name));
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register((itemGroup) -> itemGroup.accept(ModBlocks.CRAFTING_CRAFT.asItem()));

    }

    public static final Block CRAFTING_CRAFT = register(
            "crafting_craft",
            CraftingCraftBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.WOOD).mapColor(MapColor.WOOD).strength(2.5F),
            true
    );

    public static final Block GOLD_CUP = register(
            "gold_cup",
            GoldCupBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).strength(3.0F, 1200.0F),
            false
    );


}


