package malbyx.mod.epica;

import malbyx.mod.epica.FastCart.FastCartItem;
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
import net.minecraft.world.level.material.MapColor;

import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraft.world.item.Items.registerItem;

public class ModItems {
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction, Item.Properties properties) {
        return registerItem((ResourceKey)blockIdToItemId(block.builtInRegistryHolder().key()), (propertiesx) -> (Item)biFunction.apply(block, propertiesx), properties.useBlockDescriptionPrefix());
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> resourceKey) {
        return ResourceKey.create(Registries.ITEM, resourceKey.identifier());
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE));

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS)
                .register((itemGroup) -> itemGroup.accept(ModItems.FAST_CART));

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
                .register((itemGroup) -> itemGroup.accept(ModItems.GOLD_CUP));
    }

    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, (new Item.Properties()));

    public static final Item FAST_CART = register("fast_cart", (properties -> new FastCartItem(ModEntities.FAST_CART, properties)), (new Item.Properties()).stacksTo(1));

    //public static final BlockItem GOLD_CUP = register("gold_cup2", (properties -> new BlockItem(ModBlocks.GOLD_CUP, properties)), (new Item.Properties().stacksTo(16)));

    public static final Item GOLD_CUP = registerBlock(ModBlocks.GOLD_CUP, BlockItem::new, (new Item.Properties()).stacksTo(16));
}
