package malbyx.mod.epica;

import malbyx.mod.epica.CraftingCraft.CraftingCraftMenu;
import malbyx.mod.epica.GoldCup.GoldCupMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuType {
    public static final MenuType<CraftingCraftMenu> CRAFTING_CRAFT = register("crafting_craft", CraftingCraftMenu::new);
    public static final MenuType<GoldCupMenu> GOLD_CUP = register("gold_cup", GoldCupMenu::new);

    public static <T extends AbstractContainerMenu> MenuType<T> register(
            String name,
            MenuType.MenuSupplier<T> constructor
    ) {
        return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(constructor, FeatureFlagSet.of()));
    }

    public static void initialize() {}
}
