package malbyx.mod.epica.CraftingStick;

import malbyx.mod.epica.ModEpica;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public class CraftingStick extends Item {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public CraftingStick(Properties properties) {
        super(properties);
    }

    protected MenuProvider getMenuProvider(Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((i, inventory, player) -> new CraftingStickMenu(i, inventory, ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE);
    }

    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            var pos = player.position();
            var bp = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
            player.openMenu(getMenuProvider(level, bp));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }

        return InteractionResult.SUCCESS;
    }
}
