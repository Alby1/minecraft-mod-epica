package malbyx.mod.epica;

import malbyx.mod.epica.CraftingStick.CraftingStick;
import malbyx.mod.epica.FastCart.FastCartItem;
import malbyx.mod.epica.GoldCup.GoldCupItem;
import malbyx.mod.epica.Grattugia.Grattugia;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

    private static void AddToCreativeTab(ResourceKey<CreativeModeTab> tab, Item item) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register((itemGroup) -> itemGroup.accept(item));
    }

    public static void initialize() {
        AddToCreativeTab(CreativeModeTabs.INGREDIENTS, ModItems.SUSPICIOUS_SUBSTANCE);
        AddToCreativeTab(CreativeModeTabs.REDSTONE_BLOCKS, ModItems.FAST_CART);
        AddToCreativeTab(CreativeModeTabs.BUILDING_BLOCKS, ModItems.GOLD_CUP);
        AddToCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES, ModItems.CRAFTING_STICK);

        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.GUANCIALE_CRUDO);
        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.GUANCIALE_COTTO);
        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.PECORINO);
        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.PECORINO_GRATTUGIATO);
        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.CARBONARA);
        AddToCreativeTab(CreativeModeTabs.FOOD_AND_DRINKS, ModItems.MACCHERONI);

        AddToCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES, ModItems.GRATTUGIA);

    }

    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0).alwaysEdible().build())));

    public static final Item FAST_CART = register("fast_cart", (properties -> new FastCartItem(ModEntities.FAST_CART, properties)), (new Item.Properties()).stacksTo(1));

    //public static final BlockItem GOLD_CUP = register("gold_cup2", (properties -> new BlockItem(ModBlocks.GOLD_CUP, properties)), (new Item.Properties().stacksTo(16)));

    public static final Item GOLD_CUP = registerBlock(ModBlocks.GOLD_CUP, GoldCupItem::new, (new Item.Properties()).stacksTo(16));

    public static final Item CRAFTING_STICK = register("crafting_stick", CraftingStick::new, (new Item.Properties()));

    //carbonara

    public static final Item GUANCIALE_CRUDO = register("guanciale_crudo", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0).build())));
    public static final Item GUANCIALE_COTTO = register("guanciale_cotto", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(4).saturationModifier(4).build())));

    public static final Item GRATTUGIA = register("grattugia", Grattugia::new, (new Item.Properties().stacksTo(1)));

    public static final Item PECORINO = register("pecorino", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationModifier(2).build())));
    public static final Item PECORINO_GRATTUGIATO = register("pecorino_grattugiato", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(2).saturationModifier(0).build())));

    public static final Item MACCHERONI = register("maccheroni", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier(0).build())));

    public static final Item CARBONARA = register("carbonara", Item::new, (new Item.Properties().food((new FoodProperties.Builder()).nutrition(10).saturationModifier(10).build())));
}
