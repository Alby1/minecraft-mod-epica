package malbyx.mod.epica.GoldCup;

import malbyx.mod.epica.ModMenuType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class GoldCupMenu extends AbstractContainerMenu {
    public GoldCupMenu(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    public GoldCupMenu(int i, Inventory inventory) {
        super(ModMenuType.GOLD_CUP, i);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
