package malbyx.mod.epica.CraftingStick;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CraftingStickMenu extends CraftingMenu {
    public CraftingStickMenu(int i, Inventory inventory) {
        super(i, inventory);
    }

    public CraftingStickMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(i, inventory, containerLevelAccess);
    }


    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
